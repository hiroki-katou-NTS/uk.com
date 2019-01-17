package nts.uk.file.at.app.export.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.CommonAuthorDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.DateAuthorityDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.ModifyDeadlineDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PerWorkplaceDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PermissonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PermissonFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PersAuthorityDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.ShiftPermissonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleAuthorityDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleCommonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleDateDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleDescriptionDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleShiftDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleWorkplaceDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;

/**
 * 
 * @author thangnv
 *
 */

@Stateless
public class AuthorityFuncControlSheet extends JpaRepository{
	// roletype fixed 
	private static final int roleType = 3; 
	private static final String select = "○";	
	private static final String unselect = "-";
	
	@Inject
	private PermissonFinder permissonFinder;
	
	private final static String GET_BY_ROLE_TYPE = "SELECT ROLE_ID, ROLE_CD, CID, ROLE_TYPE, ROLE_NAME FROM SACMT_ROLE WHERE CID = ?companyId AND ROLE_TYPE = " + roleType + " ORDER BY ASSIGN_ATR, ROLE_CD";
	
	/**
	 * init data using some common available
	 * @return
	 */
	public Map<String, Object> initSheet() {
		String companyId = AppContexts.user().companyId();
		int maxColumn = 0;
		List<SacmtRoleData> listSacmtRoles = findRoles(companyId);
		Map<String, Object> map = new HashMap<>();
		if (listSacmtRoles.isEmpty()){
			map.put("listColumns", new ArrayList<>());
			map.put("listDatas", new ArrayList<>());
			return map;
		}
		
		ScheduleDescriptionDto scheduleDescriptionDto = permissonFinder.getAllDes();
		
		List<String> listColumnNames = new ArrayList<>();
		List<String> listCommonAuthor = new ArrayList<>();
		List<String> listPerWorkplace = new ArrayList<>();
		List<String> listPersAuthority = new ArrayList<>();
		List<String> listDateAuthority = new ArrayList<>();
		List<String> listShiftPermisson = new ArrayList<>();
		List<String> listSchemodifyDeadline = new ArrayList<>();
		
		List<Map<String, Object>> listItems = new ArrayList<>();
		
		for (SacmtRoleData role : listSacmtRoles) {
			String roleId = role.getRoleId();
			PermissonDto permissonDto = permissonFinder.getAll(roleId);
			
			Map<String, Object> mapRole = new HashMap<>();
			mapRole.put("data", permissonDto);
			mapRole.put("role", role);
			listItems.add(mapRole);
		}
		
		if (scheduleDescriptionDto != null){
			List<ScheduleCommonDto> commonAuthor = scheduleDescriptionDto.getCommon();
			if (!commonAuthor.isEmpty()){
				for (ScheduleCommonDto au : commonAuthor) {
					String columnName = TextResource.localize("KSM011_82").concat(" ");
					columnName = columnName.concat(au.getDisplayNameCom());
					if (!listCommonAuthor.contains(columnName)) listCommonAuthor.add(columnName);
				}
			}
			
			List<ScheduleWorkplaceDto> perWorkplace = scheduleDescriptionDto.getWorkplace();
			if (!perWorkplace.isEmpty()){
				for (ScheduleWorkplaceDto pe : perWorkplace) {
					String columnName = TextResource.localize("KSM011_83").concat(" ");
					columnName = columnName.concat(pe.getDisplayNameWork());
					if (!listPerWorkplace.contains(columnName)) listPerWorkplace.add(columnName);
				}
			}
			
			List<ScheduleAuthorityDto> persAuthority = scheduleDescriptionDto.getAuthority();
			if (!persAuthority.isEmpty()){
				for (ScheduleAuthorityDto pe : persAuthority) {
					String columnName = TextResource.localize("KSM011_84").concat(" ");
					columnName = columnName.concat(pe.getDisplayNameAuth());
					if (!listPersAuthority.contains(columnName)) listPersAuthority.add(columnName);
				}
			}
			
			List<ScheduleDateDto> dateAuthority = scheduleDescriptionDto.getDate();
			if (!dateAuthority.isEmpty()){
				for (ScheduleDateDto da : dateAuthority) {
					String columnName = TextResource.localize("KSM011_85").concat(" ");
					columnName = columnName.concat(da.getDisplayNameDate());
					if (!listDateAuthority.contains(columnName)) listDateAuthority.add(columnName);
				}
			}
			
			List<ScheduleShiftDto> shiftPermisson = scheduleDescriptionDto.getShift();
			if (!shiftPermisson.isEmpty()){
				for (ScheduleShiftDto sh : shiftPermisson) {
					String columnName = TextResource.localize("KSM011_86").concat(" ");
					columnName = columnName.concat(sh.getDisplayNameShift());
					if (!listShiftPermisson.contains(columnName)) listShiftPermisson.add(columnName);
				}
			}
			
			String columnName = TextResource.localize("KSM011_87").concat(" ").concat(TextResource.localize("KSM011_8"));
			if (!listSchemodifyDeadline.contains(columnName)) listSchemodifyDeadline.add(columnName);
		}
		
		listColumnNames.addAll(listCommonAuthor);
		listColumnNames.addAll(listPerWorkplace);
		listColumnNames.addAll(listPersAuthority);
		listColumnNames.addAll(listDateAuthority);
		listColumnNames.addAll(listShiftPermisson);
		listColumnNames.addAll(listSchemodifyDeadline);
		
		List<MasterHeaderColumn> listColumns = getHeaderColumns(maxColumn, listColumnNames);
		List<MasterData> listDatas = getMasterDatas(maxColumn, listItems, scheduleDescriptionDto, listColumnNames);
		map.put("listColumns", listColumns);
		map.put("listDatas", listDatas);
		return map;
	}
	
	/**
	 * get header
	 * @param columnNum
	 * @param listColumnNames
	 * @return
	 */
	public List<MasterHeaderColumn> getHeaderColumns(int columnNum, List<String> listColumnNames) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM011_133"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM011_134"), ColumnTextAlign.LEFT, "", true));
		for (String colum : listColumnNames) {
			columns.add(new MasterHeaderColumn(colum, colum, ColumnTextAlign.LEFT, "", true));
		}
		return columns;
	}
	
	/**
	 * get master data
	 * @param columnNum
	 * @param listRoleAndData
	 * @param scheduleDescriptionDto
	 * @param listColumnNames
	 * @return
	 */
	public List<MasterData> getMasterDatas(int columnNum, List<Map<String, Object>> listRoleAndData, ScheduleDescriptionDto scheduleDescriptionDto, List<String> listColumnNames) {
	
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listRoleAndData)) {
			return null;
		} else {
			// loop listSacmtRoles 
			listRoleAndData.stream().forEach(mapItem -> {
					datas.add(createRow(mapItem, scheduleDescriptionDto, listColumnNames));
			});
		}
		return datas;
	}
	
	/**
	 * get all role by company (using native SQL)
	 * @param companyId
	 * @return
	 */
	public List<SacmtRoleData> findRoles(String companyId) {
		
		List<?> data = this.getEntityManager().createNativeQuery(GET_BY_ROLE_TYPE)
				.setParameter("companyId", companyId).getResultList();
		return data.stream().map(x -> transferData((Object[])x)).collect(Collectors.toList());
	}
	
	/**
	 * transfer data query from findRoles function to new instance of SacmtRoleData
	 * @param entity
	 * @return
	 */
	private static SacmtRoleData transferData(Object[] entity){
		String roleId = null;
		String code = null;
		String cid = null;
		String roleType = null;
		String name = null;
		if (entity[0] != null){
			roleId = String.valueOf(entity[0]);
		}
		if (entity[1] != null){
			code = String.valueOf(entity[1]);
		}
		if (entity[2] != null){
			cid = String.valueOf(entity[2]);
		}
		if (entity[3] != null){
			roleType = String.valueOf(entity[3]);
		}
		if (entity[4] != null){
			name = String.valueOf(entity[4]);
		}
		SacmtRoleData domain = new SacmtRoleData(roleId, code, cid, roleType, name);
		return domain;
	}
	
	/* put empty map */
	private void putEmptyData(Map<String, Object> data, List<String> listColumnNames){
		data.put("項目","");
		data.put("値", "");
		for (String column : listColumnNames) {
			data.put(column, "");
		}
	}
	
	/**
	 * create row
	 * @param role
	 * @param scheduleDescriptionDto
	 * @param listColumnNames
	 * @return
	 */
	private MasterData createRow (Map<String, Object> role, ScheduleDescriptionDto scheduleDescriptionDto, List<String> listColumnNames){
		SacmtRoleData roleData = (SacmtRoleData) role.get("role");
		PermissonDto permissonDto = (PermissonDto) role.get("data");
		Map<String, Object> data = new HashMap<>();
		putEmptyData(data, listColumnNames); 
		
		data.put("コード", roleData.getCode());
		data.put("名称", roleData.getName());
		
		Map<String, Object> mapRole = new HashMap<>();
		mapRole.put("data", permissonDto);
		mapRole.put("role", role);
		
		List<CommonAuthorDto> commonAuthor = permissonDto.getCommonAuthor();
		if (!commonAuthor.isEmpty()){
			for (CommonAuthorDto au : commonAuthor) {
				String columnName = TextResource.localize("KSM011_82").concat(" ");
				String s = getScheduleName(scheduleDescriptionDto, au.getFunctionNoCommon().intValue(), 1);
				if (s != null){
					columnName = columnName.concat(s);
					if (au.getAvailableCommon() == 0) {
						data.put(columnName, unselect);
					} else {
						data.put(columnName, select);
					}
				}
			}
		} 
		
		List<PersAuthorityDto> persAuthority = permissonDto.getPersAuthority();
		if (!persAuthority.isEmpty()){
			for (PersAuthorityDto pe : persAuthority) {
				String columnName = TextResource.localize("KSM011_84").concat(" ");
				String s = getScheduleName(scheduleDescriptionDto, pe.getFunctionNoPers().intValue(), 2);
				if (s != null){
					columnName = columnName.concat(s);
					if (pe.getAvailablePers() == 0) {
						data.put(columnName, unselect);
					} else {
						data.put(columnName, select);
					}
				}
			}
		}
		
		List<DateAuthorityDto> dateAuthority = permissonDto.getDateAuthority();
		if (!dateAuthority.isEmpty()){
			for (DateAuthorityDto da : dateAuthority) {
				String columnName = TextResource.localize("KSM011_85").concat(" ");
				String s = getScheduleName(scheduleDescriptionDto, da.getFunctionNoDate().intValue(), 3);
				if (s != null){
					columnName = columnName.concat(s);
					if (da.getAvailableDate() == 0) {
						data.put(columnName, unselect);
					} else {
						data.put(columnName, select);
					}
				}
			}
		}
		
		List<ShiftPermissonDto> shiftPermisson = permissonDto.getShiftPermisson();
		if (!shiftPermisson.isEmpty()){
			for (ShiftPermissonDto sh : shiftPermisson) {
				String columnName = TextResource.localize("KSM011_86").concat(" ");
				String s = getScheduleName(scheduleDescriptionDto, sh.getFunctionNoShift().intValue(), 4);
				if (s != null){
					columnName = columnName.concat(s);
					if (sh.getAvailableShift() == 0) {
						data.put(columnName, unselect);
					} else {
						data.put(columnName, select);
					}
				}
			}
		}
		
		List<PerWorkplaceDto> perWorkplace = permissonDto.getPerWorkplace();
		if (!perWorkplace.isEmpty()){
			for (PerWorkplaceDto pe : perWorkplace) {
				String columnName = TextResource.localize("KSM011_83").concat(" ");
				String s = getScheduleName(scheduleDescriptionDto, pe.getFunctionNoWorkplace().intValue(), 5);
				if (s != null){
					columnName = columnName.concat(s);
					if (pe.getAvailableWorkplace() == 0) {
						data.put(columnName, unselect);
					} else {
						data.put(columnName, select);
					}
				}
			}
		}
		
		List<ModifyDeadlineDto> schemodifyDeadline = permissonDto.getSchemodifyDeadline();
		if (!schemodifyDeadline.isEmpty()){
			for (ModifyDeadlineDto sc : schemodifyDeadline) {
				String columnName = TextResource.localize("KSM011_87").concat(" ").concat(TextResource.localize("KSM011_8"));
				String value = String.valueOf(sc.getCorrectDeadline());
				value = value.concat(" ");
				value = value.concat(TextResource.localize("KSM011_135"));
				value = value.concat(TextResource.localize("KSM011_91"));
				
				data.put(columnName, value);
			}
		}
		
		MasterData masterData = new MasterData(data, null, "");
		
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		for (String string : listColumnNames) {
			masterData.cellAt(string).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
		
		return masterData;
	}
	
	/**
	 * getScheduleName
	 * @return String description
	 */
	private String getScheduleName(ScheduleDescriptionDto scheduleDescriptionDto, int functionNo, int type){
		String description = null;
		switch (type) {
		case 1:
			List<ScheduleCommonDto> common = scheduleDescriptionDto.getCommon();
			if (!common.isEmpty()){
				for (ScheduleCommonDto co : common) {
					if (functionNo == co.getFunctionNoCom()) {
						description = co.getDisplayNameCom();
						break;
					}
				}
			}
			break;
		case 2:
			List<ScheduleAuthorityDto> authority = scheduleDescriptionDto.getAuthority();
			if (!authority.isEmpty()){
				for (ScheduleAuthorityDto au : authority) {
					if (functionNo == au.getFunctionNoAuth()) {
						description = au.getDisplayNameAuth();
						break;
					}
				}
			}
			break;
		case 3:
			List<ScheduleDateDto> date = scheduleDescriptionDto.getDate();
			if (!date.isEmpty()){
				for (ScheduleDateDto da : date) {
					if (functionNo == da.getFunctionNoDate()) {
						description = da.getDisplayNameDate();
						break;
					}
				}
			}
			break;
		case 4:
			List<ScheduleShiftDto> shift = scheduleDescriptionDto.getShift();
			if (!shift.isEmpty()){
				for (ScheduleShiftDto sh : shift) {
					if (functionNo == sh.getFunctionNoShift()) {
						description = sh.getDisplayNameShift();
						break;
					}
				}
			}
			break;
		case 5:
			List<ScheduleWorkplaceDto> workplace = scheduleDescriptionDto.getWorkplace();
			if (!workplace.isEmpty()){
				for (ScheduleWorkplaceDto wo : workplace) {
					if (functionNo == wo.getFunctionNoWork()) {
						description = wo.getDisplayNameWork();
						break;
					}
				}
			}
			break;
		default:
			break;
		}
		return  description;
	}
}
