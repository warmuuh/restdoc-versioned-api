package wrm;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;
import wrm.rest.Views;

@Data @Builder
public class User {

	private int id;
	private String name;
	
	@JsonView(Views.V2.class)
	private String gender;
	
}
