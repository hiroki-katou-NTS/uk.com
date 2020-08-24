package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.個人情報（人事）(Thông tin cá nhân (Nhân sự).個人情報の取得 (Get thông tin cá nhân).個人情報の取得
 * @author chungnt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetPersonInfoHR {
	
	@Inject
	private PersonalInformationRepository personInfoRepo;
	
	public List<GetPersonInfoHROutput> getPersonInfo (GetPersonInfoHRInput input) {
		
		List<PersonalInformation> domains = this.personInfoRepo.get(input);
		List<GetPersonInfoHROutput> outputs = new ArrayList<>();
		
		outputs =  domains.stream().map(m -> {
			GetPersonInfoHROutput personInfoHROutput = mapping(m);
			return personInfoHROutput;
		}).collect(Collectors.toList());
		
		return outputs;
	}
	
	private GetPersonInfoHROutput mapping (PersonalInformation domain) {
		GetPersonInfoHROutput personInfoHROutput = new GetPersonInfoHROutput();
		
		personInfoHROutput.setContractCd(domain.getContractCd());
		personInfoHROutput.setCid(domain.getCid().map(m -> m).orElse(null));
		personInfoHROutput.setCompanyCode(domain.getCompanyCode().map(m ->m).orElse(null));
		personInfoHROutput.setPId(domain.getPId());
		personInfoHROutput.setSid(domain.getScd().map(m -> m).orElse(null));
		personInfoHROutput.setScd(domain.getScd().map(m -> m).orElse(null));
		personInfoHROutput.setPersonName(domain.getPersonName().map(m -> m).orElse(null));
		personInfoHROutput.setWorkId(domain.getWorkId());
		personInfoHROutput.setWorkName(domain.getWorkName().map(m -> m).orElse(null));
		personInfoHROutput.setHistId(domain.getHistId());
		personInfoHROutput.setStartDate(domain.getStartDate());
		personInfoHROutput.setEndDate(domain.getEndDate());
		personInfoHROutput.setReleaseDate(domain.getReleaseDate().map(m -> m).orElse(null));
		personInfoHROutput.setRequestFlg(domain.getRequestFlg());
		personInfoHROutput.setRptLayoutId(domain.getRptLayoutId());
//		personInfoHROutput.setRptId(domain.getRptId().map(m -> m).orElse(null));
//		personInfoHROutput.setRptNumber(domain.getRptNumber().map(m -> m).orElse(null));
//		
//		personInfoHROutput.setStr01(domain.getStr01().map(m -> m).orElse(null));
//		personInfoHROutput.setStr02(domain.getStr02().map(m -> m).orElse(null));
//		personInfoHROutput.setStr03(domain.getStr03().map(m -> m).orElse(null));
//		personInfoHROutput.setStr04(domain.getStr04().map(m -> m).orElse(null));
//		personInfoHROutput.setStr05(domain.getStr05().map(m -> m).orElse(null));
//		personInfoHROutput.setStr06(domain.getStr06().map(m -> m).orElse(null));
//		personInfoHROutput.setStr07(domain.getStr07().map(m -> m).orElse(null));
//		personInfoHROutput.setStr08(domain.getStr08().map(m -> m).orElse(null));
//		personInfoHROutput.setStr09(domain.getStr09().map(m -> m).orElse(null));
//		personInfoHROutput.setStr10(domain.getStr10().map(m -> m).orElse(null));
//		personInfoHROutput.setStr11(domain.getStr11().map(m -> m).orElse(null));
//		personInfoHROutput.setStr12(domain.getStr12().map(m -> m).orElse(null));
//		personInfoHROutput.setStr13(domain.getStr13().map(m -> m).orElse(null));
//		personInfoHROutput.setStr14(domain.getStr14().map(m -> m).orElse(null));
//		personInfoHROutput.setStr15(domain.getStr15().map(m -> m).orElse(null));
//		personInfoHROutput.setStr16(domain.getStr16().map(m -> m).orElse(null));
//		personInfoHROutput.setStr17(domain.getStr17().map(m -> m).orElse(null));
//		personInfoHROutput.setStr18(domain.getStr18().map(m -> m).orElse(null));
//		personInfoHROutput.setStr19(domain.getStr19().map(m -> m).orElse(null));
//		personInfoHROutput.setStr20(domain.getStr20().map(m -> m).orElse(null));
//		personInfoHROutput.setStr21(domain.getStr21().map(m -> m).orElse(null));
//		personInfoHROutput.setStr22(domain.getStr22().map(m -> m).orElse(null));
//		personInfoHROutput.setStr23(domain.getStr23().map(m -> m).orElse(null));
//		personInfoHROutput.setStr24(domain.getStr24().map(m -> m).orElse(null));
//		personInfoHROutput.setStr25(domain.getStr25().map(m -> m).orElse(null));
//		personInfoHROutput.setStr26(domain.getStr26().map(m -> m).orElse(null));
//		personInfoHROutput.setStr27(domain.getStr27().map(m -> m).orElse(null));
//		personInfoHROutput.setStr28(domain.getStr28().map(m -> m).orElse(null));
//		personInfoHROutput.setStr29(domain.getStr29().map(m -> m).orElse(null));
//		personInfoHROutput.setStr30(domain.getStr30().map(m -> m).orElse(null));
//		personInfoHROutput.setStr31(domain.getStr31().map(m -> m).orElse(null));
//		personInfoHROutput.setStr32(domain.getStr32().map(m -> m).orElse(null));
//		personInfoHROutput.setStr33(domain.getStr33().map(m -> m).orElse(null));
//		personInfoHROutput.setStr34(domain.getStr34().map(m -> m).orElse(null));
//		personInfoHROutput.setStr35(domain.getStr35().map(m -> m).orElse(null));
//		personInfoHROutput.setStr36(domain.getStr36().map(m -> m).orElse(null));
//		personInfoHROutput.setStr37(domain.getStr37().map(m -> m).orElse(null));
//		personInfoHROutput.setStr38(domain.getStr38().map(m -> m).orElse(null));
//		personInfoHROutput.setStr39(domain.getStr39().map(m -> m).orElse(null));
//		personInfoHROutput.setStr40(domain.getStr40().map(m -> m).orElse(null));
//		personInfoHROutput.setStr41(domain.getStr41().map(m -> m).orElse(null));
//		personInfoHROutput.setStr42(domain.getStr42().map(m -> m).orElse(null));
//		personInfoHROutput.setStr43(domain.getStr43().map(m -> m).orElse(null));
//		personInfoHROutput.setStr44(domain.getStr44().map(m -> m).orElse(null));
//		personInfoHROutput.setStr45(domain.getStr45().map(m -> m).orElse(null));
//		personInfoHROutput.setStr46(domain.getStr46().map(m -> m).orElse(null));
//		personInfoHROutput.setStr47(domain.getStr47().map(m -> m).orElse(null));
//		personInfoHROutput.setStr48(domain.getStr48().map(m -> m).orElse(null));
//		personInfoHROutput.setStr49(domain.getStr49().map(m -> m).orElse(null));
//		personInfoHROutput.setStr50(domain.getStr50().map(m -> m).orElse(null));
//		personInfoHROutput.setStr51(domain.getStr51().map(m -> m).orElse(null));
//		personInfoHROutput.setStr52(domain.getStr52().map(m -> m).orElse(null));
//		personInfoHROutput.setStr53(domain.getStr53().map(m -> m).orElse(null));
//		personInfoHROutput.setStr54(domain.getStr54().map(m -> m).orElse(null));
//		personInfoHROutput.setStr55(domain.getStr55().map(m -> m).orElse(null));
//		personInfoHROutput.setStr56(domain.getStr56().map(m -> m).orElse(null));
//		personInfoHROutput.setStr57(domain.getStr57().map(m -> m).orElse(null));
//		personInfoHROutput.setStr58(domain.getStr58().map(m -> m).orElse(null));
//		personInfoHROutput.setStr59(domain.getStr59().map(m -> m).orElse(null));
//		personInfoHROutput.setStr60(domain.getStr60().map(m -> m).orElse(null));
//		
//		personInfoHROutput.setDate01(domain.getDate01().map(m -> m).orElse(null));
//		personInfoHROutput.setDate02(domain.getDate02().map(m -> m).orElse(null));
//		personInfoHROutput.setDate03(domain.getDate03().map(m -> m).orElse(null));
//		personInfoHROutput.setDate04(domain.getDate04().map(m -> m).orElse(null));
//		personInfoHROutput.setDate05(domain.getDate05().map(m -> m).orElse(null));
//		personInfoHROutput.setDate06(domain.getDate06().map(m -> m).orElse(null));
//		personInfoHROutput.setDate07(domain.getDate07().map(m -> m).orElse(null));
//		personInfoHROutput.setDate08(domain.getDate08().map(m -> m).orElse(null));
//		personInfoHROutput.setDate09(domain.getDate09().map(m -> m).orElse(null));
//		personInfoHROutput.setDate10(domain.getDate10().map(m -> m).orElse(null));
//		
//		personInfoHROutput.setInt01(domain.getInt01());
//		personInfoHROutput.setInt02(domain.getInt02());
//		personInfoHROutput.setInt03(domain.getInt03());
//		personInfoHROutput.setInt04(domain.getInt04());
//		personInfoHROutput.setInt05(domain.getInt05());
//		personInfoHROutput.setInt06(domain.getInt06());
//		personInfoHROutput.setInt07(domain.getInt07());
//		personInfoHROutput.setInt08(domain.getInt08());
//		personInfoHROutput.setInt09(domain.getInt09());
//		personInfoHROutput.setInt10(domain.getInt10());
//		personInfoHROutput.setInt11(domain.getInt11());
//		personInfoHROutput.setInt12(domain.getInt12());
//		personInfoHROutput.setInt13(domain.getInt13());
//		personInfoHROutput.setInt14(domain.getInt14());
//		personInfoHROutput.setInt15(domain.getInt15());
//		personInfoHROutput.setInt16(domain.getInt16());
//		personInfoHROutput.setInt17(domain.getInt17());
//		personInfoHROutput.setInt18(domain.getInt18());
//		personInfoHROutput.setInt19(domain.getInt19());
//		personInfoHROutput.setInt20(domain.getInt20());
//		personInfoHROutput.setInt21(domain.getInt21());
//		personInfoHROutput.setInt22(domain.getInt22());
//		personInfoHROutput.setInt23(domain.getInt23());
//		personInfoHROutput.setInt24(domain.getInt24());
//		personInfoHROutput.setInt25(domain.getInt25());
//		personInfoHROutput.setInt26(domain.getInt26());
//		personInfoHROutput.setInt27(domain.getInt27());
//		personInfoHROutput.setInt28(domain.getInt28());
//		personInfoHROutput.setInt29(domain.getInt29());
//		personInfoHROutput.setInt30(domain.getInt30());
//		
//		personInfoHROutput.setNumber01(domain.getNumber01());
//		personInfoHROutput.setNumber02(domain.getNumber02());
//		personInfoHROutput.setNumber03(domain.getNumber03());
//		personInfoHROutput.setNumber04(domain.getNumber04());
//		personInfoHROutput.setNumber05(domain.getNumber05());
//		personInfoHROutput.setNumber06(domain.getNumber06());
//		personInfoHROutput.setNumber07(domain.getNumber07());
//		personInfoHROutput.setNumber08(domain.getNumber08());
//		personInfoHROutput.setNumber09(domain.getNumber09());
//		personInfoHROutput.setNumber10(domain.getNumber10());
//		personInfoHROutput.setNumber11(domain.getNumber11());
//		personInfoHROutput.setNumber12(domain.getNumber12());
//		personInfoHROutput.setNumber13(domain.getNumber13());
//		personInfoHROutput.setNumber14(domain.getNumber14());
//		personInfoHROutput.setNumber15(domain.getNumber15());
//		personInfoHROutput.setNumber16(domain.getNumber16());
//		personInfoHROutput.setNumber17(domain.getNumber17());
//		personInfoHROutput.setNumber18(domain.getNumber18());
//		personInfoHROutput.setNumber19(domain.getNumber19());
//		personInfoHROutput.setNumber20(domain.getNumber20());
//		personInfoHROutput.setNumber21(domain.getNumber21());
//		personInfoHROutput.setNumber22(domain.getNumber22());
//		personInfoHROutput.setNumber23(domain.getNumber23());
//		personInfoHROutput.setNumber24(domain.getNumber24());
//		personInfoHROutput.setNumber25(domain.getNumber25());
//		personInfoHROutput.setNumber26(domain.getNumber26());
//		personInfoHROutput.setNumber27(domain.getNumber27());
//		personInfoHROutput.setNumber28(domain.getNumber28());
//		personInfoHROutput.setNumber29(domain.getNumber29());
//		personInfoHROutput.setNumber30(domain.getNumber30());
//		
//		personInfoHROutput.setSelectId01(domain.getSelectId01());
//		personInfoHROutput.setSelectId02(domain.getSelectId02());
//		personInfoHROutput.setSelectId03(domain.getSelectId03());
//		personInfoHROutput.setSelectId04(domain.getSelectId04());
//		personInfoHROutput.setSelectId05(domain.getSelectId05());
//		personInfoHROutput.setSelectId06(domain.getSelectId06());
//		personInfoHROutput.setSelectId07(domain.getSelectId07());
//		personInfoHROutput.setSelectId08(domain.getSelectId08());
//		personInfoHROutput.setSelectId09(domain.getSelectId09());
//		personInfoHROutput.setSelectId10(domain.getSelectId10());
//		personInfoHROutput.setSelectId11(domain.getSelectId11());
//		personInfoHROutput.setSelectId12(domain.getSelectId12());
//		personInfoHROutput.setSelectId13(domain.getSelectId13());
//		personInfoHROutput.setSelectId14(domain.getSelectId14());
//		personInfoHROutput.setSelectId15(domain.getSelectId15());
//		personInfoHROutput.setSelectId16(domain.getSelectId16());
//		personInfoHROutput.setSelectId17(domain.getSelectId17());
//		personInfoHROutput.setSelectId18(domain.getSelectId18());
//		personInfoHROutput.setSelectId19(domain.getSelectId19());
//		personInfoHROutput.setSelectId20(domain.getSelectId20());
		
		if (domain.getSelectCode01().isPresent()) {
			personInfoHROutput.setSelectCode01(domain.getSelectCode01().get());
		}
		personInfoHROutput.setSelectCode02(domain.getSelectCode02().map(m -> m).orElse(""));
//		personInfoHROutput.setSelectCode03(domain.getSelectCode03().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode04(domain.getSelectCode04().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode05(domain.getSelectCode05().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode06(domain.getSelectCode06().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode07(domain.getSelectCode07().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode08(domain.getSelectCode08().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode09(domain.getSelectCode09().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode10(domain.getSelectCode10().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode11(domain.getSelectCode11().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode12(domain.getSelectCode12().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode13(domain.getSelectCode13().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode14(domain.getSelectCode14().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode15(domain.getSelectCode15().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode16(domain.getSelectCode16().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode17(domain.getSelectCode17().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode18(domain.getSelectCode18().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode19(domain.getSelectCode19().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectCode20(domain.getSelectCode20().map(m -> m).orElse(null));
//		
//		personInfoHROutput.setSelectName01(domain.getSelectName01().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName02(domain.getSelectName02().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName03(domain.getSelectName03().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName04(domain.getSelectName04().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName05(domain.getSelectName05().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName06(domain.getSelectName06().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName07(domain.getSelectName07().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName08(domain.getSelectName08().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName09(domain.getSelectName09().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName10(domain.getSelectName10().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName11(domain.getSelectName11().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName12(domain.getSelectName12().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName13(domain.getSelectName13().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName14(domain.getSelectName14().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName15(domain.getSelectName15().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName16(domain.getSelectName16().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName17(domain.getSelectName17().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName18(domain.getSelectName18().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName19(domain.getSelectName19().map(m -> m).orElse(null));
//		personInfoHROutput.setSelectName20(domain.getSelectName20().map(m -> m).orElse(null));
		
		return personInfoHROutput;
	}
}