package nts.uk.cnv.dom.td.alteration;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Strings;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

/**
 * oruta
 */
@Value
public class Alteration implements Comparable<Alteration> {

	/** oruta ID */
	String alterId;

	/** Feature ID */
	String featureId;

	/** 作成日時 */
	GeneralDateTime createdAt;

	/** テーブルID */
	String tableId;

	/** メタ情報 */
	AlterationMetaData metaData;

	/** 内容 */
	List<AlterationContent> contents;

	/**
	 * 新しいテーブルを作成する
	 * @param featureId
	 * @param meta
	 * @param newTable
	 * @return
	 */
	public static Alteration newTable(
			String featureId,
			AlterationMetaData meta,
			TableDesign newTable) {

		return create(featureId, meta, Optional.empty(), Optional.of(newTable)).get();
	}

	/**
	 * 既存のテーブルを変更する
	 * @param featureId
	 * @param meta
	 * @param base
	 * @param altered
	 * @return
	 */
	public static Optional<Alteration> alter(
			String featureId,
			AlterationMetaData meta,
			TableDesign base,
			TableDesign altered) {

		return create(featureId, meta, Optional.of(base), Optional.of(altered));
	}

	private static Optional<Alteration> create(
			String featureId,
			AlterationMetaData meta,
			Optional<? extends TableDesign> base,
			Optional<TableDesign> altered) {
		
		if (Strings.isNullOrEmpty(featureId)) {
			throw new BusinessException(new RawErrorMessage("Featureを指定してください"));
		}

		if (base.equals(altered)) {
			return Optional.empty();
		}

		// 基本的にはbaseのIDで良いはず。
		// baseが無いなら新規テーブルなので、alteredのテーブル名を採用
		// テーブルIDの知識はここに置きたくないが・・・
		String tableId = base.map(b -> b.getId())
				.orElseGet(() -> altered.get().getName().v());

		val contents = Arrays.stream(AlterationType.values())
			.filter(type -> type.applicable(base, altered))
			.flatMap(type -> type.createContent(base, altered).stream())
			.collect(toList());
		
		if (contents.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(new Alteration(
				IdentifierUtil.randomUniqueId(),
				featureId,
				GeneralDateTime.now(),
				tableId,
				meta,
				contents));
	}

	/**
	 * 適用する
	 * @param builder
	 */
	public void apply(TableProspectBuilder builder) {
		for(AlterationContent c : contents) {
			c.apply(this.alterId, builder);
		}
	}

	/**
	 * 差分DDLを生成する
	 * @param require
	 * @param tableDesign テーブル定義
	 * @param defineType RDBMSごとに違う仕様を隠蔽するインタフェースのインスタンス
	 * @return
	 */
	public String createAlterDdl(TableProspectBuilder builder, TableDefineType defineType) {
		List<String> ddls = new ArrayList<>();
		for(AlterationContent c : contents) {
			TableDesign tableDesign = (TableDesign) builder.build()
					.orElseGet(null);

			if(tableDesign == null && c.getType() != AlterationType.TABLE_CREATE) {
				throw new BusinessException(
						new RawErrorMessage("存在しないテーブルに対して変更を適用できません。alterationId:" + this.alterId));
			}
			String ddl = c.createAlterDdl(tableDesign, defineType);
			ddls.add(ddl);
		}
		return String.join("\r\n", ddls) + "\r\n";
	}

	@Override
	public int compareTo(Alteration other) {
		return this.createdAt.compareTo(other.createdAt);
	}

	@SuppressWarnings("unchecked")
	public <C extends AlterationContent> List<C> getContents(Class<C> target) {

		return this.contents.stream()
				.filter(c -> c.getClass().equals(target))
				.map(c -> (C) c)
				.collect(toList());
	}

	/**
	 * alterId以外が一致しているか（UnitTest用）
	 * @param obj
	 * @return
	 */
	public boolean equalsExcludingId(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alteration other = (Alteration) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (metaData == null) {
			if (other.metaData != null)
				return false;
		} else if (!metaData.equals(other.metaData))
			return false;
		if (tableId == null) {
			if (other.tableId != null)
				return false;
		} else if (!tableId.equals(other.tableId))
			return false;
		return true;
	}

}
