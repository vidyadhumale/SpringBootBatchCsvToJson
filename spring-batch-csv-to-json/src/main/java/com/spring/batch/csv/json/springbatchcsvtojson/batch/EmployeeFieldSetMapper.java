package com.spring.batch.csv.json.springbatchcsvtojson.batch;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.spring.batch.csv.json.springbatchcsvtojson.model.Email;
import com.spring.batch.csv.json.springbatchcsvtojson.model.Employee;
import com.spring.batch.csv.json.springbatchcsvtojson.model.PhoneNumber;

@Component
public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {

	final Logger LOGGER = LoggerFactory.getLogger(EmployeeFieldSetMapper.class);

	@Override
	public Employee mapFieldSet(FieldSet fieldSet) throws BindException {

		Employee employee = new Employee();
		employee.setFirstName(fieldSet.readString("first_name"));
		employee.setLastName(fieldSet.readString("last_name"));
		employee.setCompanyName(fieldSet.readString("company_name"));
		employee.setAddress(fieldSet.readString("address"));
		employee.setCity(fieldSet.readString("city"));
		employee.setCountry(fieldSet.readString("country"));
		employee.setState(fieldSet.readString("state"));
		employee.setZip(fieldSet.readString("zip"));

		boolean isValidPhone1 = isValidPhoneNumber(fieldSet.readString("phone1"));
		if (!isValidPhone1) {
			LOGGER.error("Invalid phone1 id " + fieldSet.readString("phone1") + " First Name "
					+ fieldSet.readString("first_name"));
			return null;
		} else {
			PhoneNumber phone1 = getPhoneNumber(fieldSet.readString("phone1"));
			employee.setPhone1(phone1);
		}

		boolean isValidPhone2 = isValidPhoneNumber(fieldSet.readString("phone2"));
		if (!isValidPhone2) {
			LOGGER.error("Invalid phone2 id " + fieldSet.readString("phone2") + " First Name "
					+ fieldSet.readString("first_name"));
			return null;
		} else {
			PhoneNumber phone2 = getPhoneNumber(fieldSet.readString("phone2"));
			employee.setPhone2(phone2);
		}

		boolean isValid = isEmailValid(fieldSet.readString("email"));
		if (isValid) {
			Email email = new Email(fieldSet.readString("email"));
			employee.setEmail(email);
		} else {
			LOGGER.error("Invalid maill id " + fieldSet.readString("email") + " First Name "
					+ fieldSet.readString("first_name"));
			return null;
		}

		employee.setWeb(fieldSet.readString("web"));
		return employee;
	}

	private PhoneNumber getPhoneNumber(String phone) {
		List<Integer> str = Stream.of(phone.split("-")).map(elem -> new Integer(elem)).collect(Collectors.toList());
		return new PhoneNumber(str.get(0), str.get(1), str.get(2));
	}

	private boolean isValidPhoneNumber(String phone) {
		Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	private boolean isEmailValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

}
