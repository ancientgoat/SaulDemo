package org.saul.controller;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/saul")
public class SaulMasterController {

	@RequestMapping(value = "/showall", method = RequestMethod.GET, produces = "application/json")
	public List<String> getDataDefinitionNames() {
		return Lists.newArrayList();
	}


	//	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	//	public String getPatient() {
	//
	//		FhirContext ctx = FhirContext.forDstu2();
	//		Patient patient = DumbRestfulPatientResourceProvider._makeFakePatient("Elmo");
	//
	//		try {
	//			String jsonString = ctx.newJsonParser().encodeResourceToString(patient);
	//			System.out.println(jsonString);
	//			return jsonString;
	//		} catch (Exception e) {
	//			System.out.println(ExceptionHelper.toString(e));
	//			throw new IllegalArgumentException(e);
	//		}
	//	}
	//
	//	@RequestMapping(value = "/dumbdto/{name}", method = RequestMethod.GET, produces = "application/json")
	//	public String getDumoDto(@PathVariable("name") String inName) {
	//
	//		try {
	//			DumbDto dumbDto = this.childEntityService.gimmeDatDumbDto(inName);
	//			return dumbDto.toString();
	//		} catch (Exception e) {
	//			System.out.println(ExceptionHelper.toString(e));
	//			throw new IllegalArgumentException(e);
	//		}
	//	}
	//
	//	@RequestMapping(value = "/dumbdtos", method = RequestMethod.GET, produces = "application/json")
	//	public String getDemDumoDtos() {
	//		try {
	//			return JsonHelper.beanToJsonString(this.childEntityService.gimmeDemDumbDtos());
	//		} catch (Exception e) {
	//			System.out.println(ExceptionHelper.toString(e));
	//			throw new IllegalArgumentException(e);
	//		}
	//	}

}
