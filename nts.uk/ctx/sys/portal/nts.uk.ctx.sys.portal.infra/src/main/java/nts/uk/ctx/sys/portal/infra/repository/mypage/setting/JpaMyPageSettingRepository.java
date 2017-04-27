package nts.uk.ctx.sys.portal.infra.repository.mypage.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.portal.dom.enums.PermissionDivision;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.primitive.CompanyId;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;

@Stateless
public class JpaMyPageSettingRepository implements MyPageSettingRepository {

	@Override
	public Optional<MyPageSetting> findByCompanyId(String companyId) {

		// mock data
		List<TopPagePartUseSetting> lstPagePartSettingItem = new ArrayList<TopPagePartUseSetting>();
		TopPagePartUseSetting item1 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("w1"),new TopPagePartName("PartName"),
				UseDivision.Use, TopPagePartType.Widget);
		TopPagePartUseSetting item2 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("d1"),new TopPagePartName("PartName"),
				UseDivision.NotUse, TopPagePartType.DashBoard);
		TopPagePartUseSetting item3 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("d2"),new TopPagePartName("PartName"),
				UseDivision.NotUse, TopPagePartType.DashBoard);
		TopPagePartUseSetting item4 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("f1"),new TopPagePartName("PartName"),
				UseDivision.Use, TopPagePartType.FolowMenu);
		TopPagePartUseSetting item5 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("f2"),new TopPagePartName("PartName"),
				UseDivision.NotUse, TopPagePartType.FolowMenu);
		TopPagePartUseSetting item6 = new TopPagePartUseSetting(new CompanyId("001"), new TopPagePartCode("d3"),new TopPagePartName("PartName"),
				UseDivision.NotUse, TopPagePartType.DashBoard);

		lstPagePartSettingItem.add(item1);
		lstPagePartSettingItem.add(item2);
		lstPagePartSettingItem.add(item3);
		lstPagePartSettingItem.add(item4);
		lstPagePartSettingItem.add(item5);
		lstPagePartSettingItem.add(item6);

		MyPageSetting mps = new MyPageSetting(new CompanyId("001"), UseDivision.Use, UseDivision.NotUse, UseDivision.Use,
				UseDivision.NotUse, PermissionDivision.Allow, lstPagePartSettingItem);
		return Optional.of(mps);
	}

	@Override
	public void update(MyPageSetting myPageSetting) {
		// TODO Auto-generated method stub
		
	}

}
