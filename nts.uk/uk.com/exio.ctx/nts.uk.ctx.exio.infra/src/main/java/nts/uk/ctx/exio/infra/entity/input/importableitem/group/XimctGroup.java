package nts.uk.ctx.exio.infra.entity.input.importableitem.group;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.group.TransactionUnit;

@Entity
@Table(name = "XIMCT_GROUP")
@AllArgsConstructor
@NoArgsConstructor
public class XimctGroup {
	
	public static final JpaEntityMapper<XimctGroup> MAPPER = new JpaEntityMapper<>(XimctGroup.class);

	@Id
	@Column(name = "GROUP_ID")
	public int groupId;

	@Column(name = "NAME")
	public String name;

	@Column(name = "AVAILABLE_MODES")
	public int availableModes;

	@Column(name = "TRANSACTION_UNIT")
	public int transactionUnit;
	
	public ImportingGroup toDomain() {
		
		return new ImportingGroup(
				ImportingGroupId.valueOf(groupId),
				name,
				AvailableModes.restore(availableModes),
				TransactionUnit.valueOf(transactionUnit));
	}
	
	public static XimctGroup toEntity(ImportingGroup domain) {
		return new XimctGroup(
				domain.getGroupId().value,
				domain.getName(),
				AvailableModes.toBits(domain.getAvailableModes()),
				domain.getTransactionUnit().value);
	}
	
	/**
	 * 利用可能なモード一覧は、今後モードそのものが増える可能性に備えて、個別のboolean列ではなく1つのbit列で表現する
	 */
	static class AvailableModes {

		static Set<ImportingMode> restore(int bits) {
			
			Set<ImportingMode> result = new HashSet<>();
			
			for (int i = 0; i < ImportingMode.values().length; i++) {
				
				int bit = (bits >> i) & 1;
				if (bit == 1) {
					result.add(ImportingMode.valueOf(i + 1));
				}
			}
			
			return result;
		}
		
		static int toBits(Set<ImportingMode> modes) {
			
			int bits = 0;
			for (val mode : modes) {
				bits += 1 << (mode.value - 1);
			}
			
			return bits;
		}
	}
}
