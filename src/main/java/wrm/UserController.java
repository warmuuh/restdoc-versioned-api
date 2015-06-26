package wrm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	
	@RequestMapping("**/user")
	public User getUser(){
		return User.builder()
				.id(1)
				.name("Horst")
				.gender("male")
				.build();
	}
	
}
