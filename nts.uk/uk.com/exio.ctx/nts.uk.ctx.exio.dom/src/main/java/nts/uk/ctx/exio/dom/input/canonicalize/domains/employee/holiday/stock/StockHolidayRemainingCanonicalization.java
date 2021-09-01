package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.stock;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 積立年休付与残数データ
 */
public class StockHolidayRemainingCanonicalization  extends IndependentCanonicalization{

	@Override
	protected String getParentTableName() {
		return "KRCDT_HDSTK_REM";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.SID, DomainDataColumn.YMD);
	}
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public StockHolidayRemainingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}


	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {

		// 受入データ内の重複チェック
		Set<List<Object>> importingKeys = new HashSet<>();
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {

			for (val interm : interms) {
				val addedFixedItem = interm.addCanonicalized(addFixedItem(interm)) ;
				val key = getPrimaryKeys(addedFixedItem, workspace);
				if (importingKeys.contains(key)) {
					throw new RuntimeException("重複データ" + key);
				}

				importingKeys.add(key);
				
				super.canonicalize(require, context, addedFixedItem, new KeyValues(key));
			}
		});
	}
	
	/**
	 *  受入時に固定の値を入れる物たち
	 */
	private CanonicalItemList addFixedItem(IntermediateResult interm) {
	    List<CanonicalItem> items = new ArrayList<>();
	    items.addAll(Arrays.asList(
	    		new CanonicalItem(100,IdentifierUtil.randomUniqueId()),
	    		new CanonicalItem(101,GrantRemainRegisterType.MANUAL.value),
	    		new CanonicalItem(102,0),
	    		new CanonicalItem(103,0),
	    		new CanonicalItem(104,0),
	    		new CanonicalItem(105,0),
	    		new CanonicalItem(106,0),
	    		new CanonicalItem(107,0)
	    ));
	    
	    return new CanonicalItemList(items);
	}
	
	private static List<Object> getPrimaryKeys(IntermediateResult record, DomainWorkspace workspace) {
		
		return workspace.getItemsPk().stream()
				.map(k -> record.getItemByNo(k.getItemNo()).get())
				.map(item -> item.getValue())
				.collect(toList());
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}

}
