package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDTOTest {

	private JSONObject onlyObjectId = new JSONObject().appendField("objectId", "123");

	@Test
	void test1() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDTO dto = objectMapper.readValue(onlyObjectId.toString(), UserDTO.class);
		assertEquals(dto.getObjectId(), "123");
		assertNull(dto.getFrequency());
	}

}