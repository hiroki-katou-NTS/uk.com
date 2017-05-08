package nts.uk.shr.com.time.japanese;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

@ApplicationScoped
public class JapaneseErasProvider {

	private JapaneseEras eras;
	
	//@Inject
	private JapaneseErasAdapter adapter;
	
	@PostConstruct
	public void initialize() {
		//this.eras = this.adapter.getAllEras();
		this.eras = new JapaneseEras(Arrays.asList(
				new JapaneseEraName("テスト１", "A", GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(1999, 12, 31)),
				new JapaneseEraName("テスト２", "B", GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31))
				));
	}
	
	public JapaneseEras getAllEras() {
		return this.eras;
	}
}
