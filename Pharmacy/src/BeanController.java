

/**
 * @author joystiq
 *
 */
public class BeanController {

	private final String email;
	private final String password;
	public String message;
	
	public BeanController(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getMessage() {
		return message;
	}
	
	public boolean validate() {
		
		if(password.length() < 8) {
			message = "password must be 8 character long";
			return false;
		}
		
		if(!email.matches("\\w+@\\w+\\.\\w+")) {
			message = "this is not a valid email please try again";
			return false;
		}
		
		else if(password.matches("\\w*\\s+\\w*")) {
			message = "password cannot contain spaces";
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanController other = (BeanController) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
