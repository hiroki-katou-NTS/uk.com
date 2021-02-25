package nts.uk.ctx.pereg.infra.repository.mastercopy.helper;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.gul.text.IdentifierUtil;

/**
 * 個人情報マスタのコピー中に新規生成されるIDを管理するコンテナ
 * コピー元データのID値を渡すと、それに対応する生成済みIDを返してくれる
 */
@RequiredArgsConstructor
public class IdContainer {
	
	/** PER_INFO_CATEGORY_ID, PER_INFO_CTG_ID */
	@Getter
	private final IdsMap categoryIds;
	
	/** PER_INFO_ITEM_DEFINITION_ID, PER_INFO_ITEM_DEF_ID */
	@Getter
	private final IdsMap itemIds;
	
	public static class IdGenerator {
		
		private final Map<String, String> map = new HashMap<>();
		
		/**
		 * 新しいIDを生成する
		 * @param sourceId コピー元データのID値
		 * @return
		 */
		public String generateFor(String sourceId) {
			
			if (map.containsKey(sourceId)) {
				throw new RuntimeException("既に生成済み：" + sourceId);
			}
			
			String newId = IdentifierUtil.randomUniqueId();
			map.put(sourceId, newId);
			
			return newId;
		}
		
		public IdsMap fix() {
			return new IdsMap(map);
		}
	}

	public static class IdsMap {
		
		private final Map<String, String> map;
		
		private IdsMap(Map<String, String> map) {
			this.map = new HashMap<>(map);
		}
		
		public IdsMap() {
			this.map = Collections.emptyMap();
		}
		
		public List<String> getAllIdsSource() {
			return map.keySet().stream().collect(toList());
		}
		
		public List<String> getAllIdsCopied() {
			return map.values().stream().collect(toList());
		}
		
		/**
		 * 指定したコピー元データID値に対応する生成済みIDを返す
		 * @param sourceId コピー元データのID値
		 * @return
		 */
		public String getFor(String sourceId) {
			
			if (!map.containsKey(sourceId)) {
				throw new RuntimeException("まだ生成していない：" + sourceId);
			}
			
			return map.get(sourceId);
		}
	}
}
