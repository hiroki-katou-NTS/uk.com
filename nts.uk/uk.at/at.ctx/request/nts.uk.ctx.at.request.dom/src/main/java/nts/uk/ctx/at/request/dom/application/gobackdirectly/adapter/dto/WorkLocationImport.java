package nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkLocationImport {
	/**
	 * 場所CD
	 */
	private String workLocationCD;
	/**
	 * 場所名
	 */
	private String workLocationName;
//	/**
//	 * 
//	 */
//	private String horiDistance;
//	/**
//	 * 
//	 */
//	private String vertiDistance;
	/**
	 * bỏ dài rộng, thay bằng bán kính (Enum Radius)
	 */
	private int radius;
	/**
	 * 
	 */
	private double latitude;
	/**
	 * 
	 */
	private double longitude;

}
