package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired//해당 클래스가 메모리에 올려지면, 해당 변수 타입에 맞는 객체가 메모리에 있으면 변수에 할당.(DI, 의존성 주입)
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(String username, String password, String email){
        //x-www.form-urelencoded는 key-value형태로 받는데 이는 스프링이 함수의 파라미터로 밸류값을 다 저장해준다.
        //@RequestParam("username") String u...이런식으로 변수명을 유연하게도 가능!
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        System.out.println("email: "+email);
        return "회원가입이 완료 되었습니다!";
    }
    @PostMapping("/dummy/join2")
    public String join2(User user){
        //객체 형태로도 가능하다!(다만 데이터의 키값이 객체의 변수명과 일치해야 함
        System.out.println("role: "+user.getRole());
        System.out.println("username: "+user.getUsername());
        System.out.println("password: "+user.getPassword());
        System.out.println("email: "+user.getEmail());
        System.out.println("createDate: "+user.getCreateDate());

        user.setRole(RoleType.USER); //User 클래스의 @DynamicInsert를 대신하는 문장.
        userRepository.save(user); //회원정보 저장.
        return "회원가입이 완료 되었습니다!";
    }

    //select
    //1. 한건의 데이터 셀렉트
    //주소에서 파라미터 가져오기
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        //findById는 optional클래스를 반환(못찾는 경우를 대비, null 사용 방지) ->반환형에 안맞네?
        //1. orElseGet메소드 사용하자.
        //optional 클래스의 orElseGet메소드는 null인 경우 해당 객체를 어떻게 채울지 구현.
        //orElseGet메소드는 supplier 인터페이스를 받는다.(익명객체로 구현)
        //2. orElseThrow 메소드 사용하자.(예외 생성)
        //supplier 제네릭에 우리가 예측한 예외를 써서 get메소드에 예외 만들어서 반환!
        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
            @Override
            public User get() {
                return new User(); //없는 아이디를 select 하려면, 빈 유저객체를 반환!
            }
        });
        User user2 = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id :"+id);
            }
        });
        //Ramda
        User user3 = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 사용자가 없네요. id: "+ id);
        });
        return user2;
        //현재 반환값은 자바 객체 형태. 이걸 웹브라우저가 읽으려면?
        //json으로 변환해줘야! (스프링부트는 MessageConverter가 응답시 작동)
        //MessageConverter 가 Jackson라이브러리를 호출해서 json으로 변환.

    }
    //2. 전체 데이터 셀렉트
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }
    //3. 한 페이지 당 2건의 데이터 리턴받기
    @GetMapping("/dummy/user")
    public List<User> list2(@PageableDefault(size = 2, sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
        //page의 사이즈 2, id 기준으로 정렬, 정렬은 내림차 정렬하는 페이지어블을 인자로 가져온다.
        //pageable를 findAll하면 기준에 맞는 페이지를 준다!(그 페이지 속 컨텐트를 뽑아와서 리스트에 저장)
        //?page=0 을 덧붙이면 첫 페이지. ?page=1을 주면 두번째 페이지..이런식
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }
    // update
    //save함수를 활용한 update

    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        //json을 입력받기 위해서 @RequestBody사용.(json -> java object)
        System.out.println("id :"+id);
        System.out.println("username: "+requestUser.getUsername());
        System.out.println("password:"+requestUser.getPassword());
        System.out.println("email:"+requestUser.getEmail());
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정 실패하였습니다.");
        });
        user.setUsername(requestUser.getUsername());
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        userRepository.save(user);
        //save메소드가 특이한게, 객체를 저장할 때 해당 id가 이미 있으면, 그 값을 update 함.
        //근데 문제는 객체에서 null 인 속성들 그대로 db에 저장시킨다는 게 문제..(일부만 업데이트할 땐 번거로움..)
        //그래서 save메소드를 활용한 update는 항상 맞는 객체를 찾아서 그 객체를 변경한 후 save메소드를 호출해야 함.
        //save 메소드는 id를 없이 사용되면 insert 기능
        //id를 넘겨주고 그 id에 맞는 데이터 값이 있으면 update
        //id를 넘겨줬는데, 그 id에 대한 데이터 없으면 insert
        return user;
    }

    //@Transactional으로 update하기.(더티 체킹)
    //save하지 않아도 업데이트 된다.(객체를 찾아서 값만 변경하면 끝!)
    @Transactional
    @PutMapping("/dummy/user2/{id}")
    public User updateUser2(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id : "+id);
        System.out.println("username: "+requestUser.getUsername());
        System.out.println("password : "+requestUser.getPassword());
        System.out.println("email: "+requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("update fail!!!!!!!");
        });
        user.setUsername(requestUser.getUsername());
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        return user;
    }

    //delete
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제 실패했습니다. 해당 id는 데이터베이스에 없습니다.";
        }

        return "삭제됐습니다! id: "+id;
    }
}
