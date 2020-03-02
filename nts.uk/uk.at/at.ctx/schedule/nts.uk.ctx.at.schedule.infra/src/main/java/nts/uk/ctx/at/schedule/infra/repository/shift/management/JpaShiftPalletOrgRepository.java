package nts.uk.ctx.at.schedule.infra.repository.shift.management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrg;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrgCombi;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrgCombiDtl;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrgCombiDtlPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrgCombiPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.KscmtPaletteOrgPk;

/**
 * 
 * @author phongtq
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftPalletOrgRepository extends JpaRepository implements ShiftPalletsOrgRepository {

	private static final String SELECT;

	private static final String FIND_BY_PAGE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a.CID, a.TARGET_UNIT, a.TARGET_ID, a.PAGE, a.PAGE_NAME, a.USE_ATR, a.NOTE,");
		builderString.append("b.POSITION, b.POSITION_NAME,");
		builderString.append("c.POSITION_ORDER, c.SHIFT_MASTER_CD");
		builderString.append(
				"FROM KSCMT_PALETTE_ORG a LEFT JOIN KSCMT_PALETTE_ORG_COMBI b ON a.TARGET_UNIT = b.TARGET_UNIT AND a.TARGET_ID = b.TARGET_ID AND a.PAGE = b.PAGE ");
		builderString.append("LEFT JOIN KSCMT_PALETTE_ORG_COMBI_DTL c ON a.TARGET_UNIT = c.TARGET_UNIT AND a.TARGET_ID = c.TARGET_ID AND a.PAGE = c.PAGE ");
		SELECT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.TARGET_UNIT = 'targetUnit' AND a.TARGET_ID = targetId AND a.PAGE = page");
		FIND_BY_PAGE = builderString.toString();

	}
	
	@AllArgsConstructor
	@Getter
	private class FullShiftPalletsOrg {
		public String companyId;
		public int targetUnit;
		public String targetId;
		public int page;
		public String pageName;
		public boolean useAtr;
		public String note;
		public int position;
		public String positionName;
		public int positionOrder;
		public String shiftMasterCd;
	}

	@SneakyThrows
	private List<FullShiftPalletsOrg> createShiftPallets(ResultSet rs) {
		List<FullShiftPalletsOrg> listFullData = new ArrayList<>();
		while (rs.next()) {
			listFullData.add(new FullShiftPalletsOrg(rs.getString("CID"),Integer.valueOf(rs.getString("TARGET_UNIT")), rs.getString("TARGET_ID"),Integer.valueOf(rs.getString("PAGE")),
					rs.getString("PAGE_NAME"), Integer.valueOf(rs.getString("USE_ATR")) == 1 ? true : false,
					rs.getString("NOTE"), Integer.valueOf(rs.getString("POSITION")), rs.getString("POSITION_NAME"),
					Integer.valueOf(rs.getString("POSITION_ORDER")), rs.getString("SHIFT_MASTER_CD")));
		}
		return listFullData;
	}

	private List<KscmtPaletteOrg> toEntity(List<FullShiftPalletsOrg> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullShiftPalletsOrg::getPage)).entrySet().stream()
				.map(x -> {
					FullShiftPalletsOrg shiftPallets = x.getValue().get(0);
					KscmtPaletteOrgPk pk = new KscmtPaletteOrgPk(shiftPallets.getCompanyId(),shiftPallets.getTargetUnit(),shiftPallets.getTargetId() , shiftPallets.getPage());
					String pageName = shiftPallets.getPageName();
					boolean useAtr = shiftPallets.isUseAtr();
					String note = shiftPallets.getNote();
					List<KscmtPaletteOrgCombi> cmpCombis = x.getValue().stream()
							.collect(Collectors.groupingBy(FullShiftPalletsOrg::getPosition)).entrySet().stream()
							.map(y -> {
								KscmtPaletteOrgCombiPk combiPk = new KscmtPaletteOrgCombiPk(
										y.getValue().get(0).getCompanyId(), y.getValue().get(0).getTargetUnit(), y.getValue().get(0).getTargetId(), y.getValue().get(0).getPage(),
										y.getValue().get(0).getPosition());
								String positionName = y.getValue().get(0).getPositionName();
								List<KscmtPaletteOrgCombiDtl> cmpCombiDtls = y.getValue().stream()
										.collect(Collectors.groupingBy(FullShiftPalletsOrg::getPositionOrder)).entrySet()
										.stream().map(z -> {
											KscmtPaletteOrgCombiDtlPk cmpCombiDtlPk = new KscmtPaletteOrgCombiDtlPk(
													z.getValue().get(0).getCompanyId(),z.getValue().get(0).getTargetUnit(),z.getValue().get(0).getTargetId(), z.getValue().get(0).getPage(),
													z.getValue().get(0).getPosition(),
													z.getValue().get(0).getPositionOrder());
											String shiftMasterCd = z.getValue().get(0).getShiftMasterCd();
											return new KscmtPaletteOrgCombiDtl(cmpCombiDtlPk, shiftMasterCd);
										}).collect(Collectors.toList());
								return new KscmtPaletteOrgCombi(combiPk, positionName);
							}).collect(Collectors.toList());
					return new KscmtPaletteOrg(pk, pageName, useAtr ? 1 : 0, note);
				}).collect(Collectors.toList());
	}

	@Override
	public void add(ShiftPalletsOrg shiftPalletsOrg) {
		commandProxy().insert(KscmtPaletteOrg.fromDomain(shiftPalletsOrg));

	}

	@Override
	public void update(ShiftPalletsOrg shiftPalletsOrg) {
		commandProxy().update(KscmtPaletteOrg.fromDomain(shiftPalletsOrg));

	}

	@Override
	public void delete(ShiftPalletsOrg shiftPalletsOrg) {
		// TODO Auto-generated method stub

	}

	@Override
	
	
	public ShiftPalletsOrg findShiftPalletOrg(int targetUnit, String targetId, int page) {
		
		String query = FIND_BY_PAGE;
		query = query.replaceFirst("targetUnit", String.valueOf(targetUnit));
		query = query.replaceFirst("targetId", targetId);
		query = query.replaceFirst("page", String.valueOf(page));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			ShiftPalletsOrg shiftPalletsOrg = toEntity(createShiftPallets(rs)).stream().map(x -> x.toDomain()).collect(Collectors.toList()).get(0);

		 return shiftPalletsOrg;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<ShiftPalletsOrg> findbyWorkPlaceId(String workplaceId) {
		List<ShiftPalletsOrg> results = new ArrayList<>();
		String query =  "SELECT PAGE, PAGE_NAME FROM KSCMT_PALETTE_ORG WHERE TARGET_ID = ?"; 
		try (PreparedStatement statement = this.connection().prepareStatement(query)) {
			statement.setString(1, workplaceId);
			results.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
				ShiftPalletDisplayInfor shiftPalletsOrg = new ShiftPalletDisplayInfor(
						new ShiftPalletName(rec.getString("PAGE_NAME")), null, null);
				ShiftPallet shiftPallet = new ShiftPallet(shiftPalletsOrg, null);
				ShiftPalletsOrg org = new ShiftPalletsOrg(null, rec.getInt("PAGE"), shiftPallet);
				return org;
			})); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public void deleteByWorkPlaceId(String workplaceId, int page) {
		// TODO Auto-generated method stub
		
	}

}
