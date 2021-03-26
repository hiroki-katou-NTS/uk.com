package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

class Healper {

	static class Alter {

		static Optional<nts.uk.cnv.dom.td.alteration.Alteration> test(TableDesign base, TableDesign altered) {
			return nts.uk.cnv.dom.td.alteration.Alteration.alter(Dummy.FEATURE_ID, Dummy.META, base, altered);
		}

		static nts.uk.cnv.dom.td.alteration.Alteration create(AlterationContent content) {
			return create(Arrays.asList(content));
		}

		static nts.uk.cnv.dom.td.alteration.Alteration create(List<AlterationContent> contents) {
			return new nts.uk.cnv.dom.td.alteration.Alteration(
					Dummy.ALTER_ID,
					Dummy.FEATURE_ID,
					GeneralDateTime.FAKED_NOW,
					Table.BASE.getId(),
					Dummy.META,
					contents);
		}
	}
	
	static class Table {
		
		static final TableDesign BASE = create(Dummy.TABLE_ID, Dummy.TABLE_NAME, Dummy.TABLE_JP_NAME, baseColumns(), baseConstraints());
		
		static TableDesign create(
				String tableId,
				TableName tableName,
				String tableJpName,
				List<ColumnDesign> columns,
				TableConstraints constraints) {
			
			return new TableDesign(
					tableId,
					tableName,
					tableJpName,
					columns,
					constraints);
		}
		
		static List<ColumnDesign> baseColumns() {
			return new ArrayList<>(Arrays.asList(
					new ColumnDesign("id1", "COL1", "列1", Column.Type.varchar(1), Dummy.COMMENT, 0),
					new ColumnDesign("id2", "COL2", "列2", Column.Type.real(3, 2), Dummy.COMMENT, 1)
					));
		}
		
		static TableConstraints baseConstraints() {
			return new TableConstraints(
					new PrimaryKey(Arrays.asList("id1"), false),
					Collections.emptyList(),
					Collections.emptyList());
		}
		
		static Builder builder() {
			return new Builder();
		}
		
		static class Builder {
			String tableId = BASE.getId();
			TableName tableName = BASE.getName();
			String tableJpName = BASE.getJpName();
			List<ColumnDesign> columns = BASE.getColumns();
			TableConstraints constraints = BASE.getConstraints();

			Builder tableId(String tableId) {
				this.tableId = tableId;
				return this;
			}
			
			Builder tableName(String tableName) {
				this.tableName = new TableName(tableName);
				return this;
			}
			
			Builder tableJpName(String tableJpName) {
				this.tableJpName = tableJpName;
				return this;
			}
			
			Builder columns(List<ColumnDesign> columns) {
				this.columns = columns;
				return this;
			}
			
			Builder constraints(TableConstraints constraints) {
				this.constraints = constraints;
				return this;
			}
			
			public TableDesign build() {
				return create(tableId,tableName, tableJpName, columns, constraints);
			}
		}
	}
	
	static class Column {
		static final ColumnDesign BASE = create(Dummy.COLUMN_ID, Dummy.COLUMN_NAME, Dummy.COLUMN_JP_NAME,Type.varchar(0),Dummy.COMMENT, 0);
		
		static ColumnDesign create(
				String columnId,
				String columnName,
				String columnJpName,
				DefineColumnType type,
				String comment,
				int dispOrder
				) {
			return new ColumnDesign(
					columnId,
					columnName,
					columnJpName,
					type,
					comment,
					dispOrder);
		}
		
		static ColumnBuilder builder(ColumnDesign base) {
			return new ColumnBuilder(base);
		}
		
		static class ColumnBuilder {
			String columnId;
			String columnName;
			String columnJpName;
			DefineColumnType type;
			String comment;
			int dispOrder;
			
			ColumnBuilder(ColumnDesign base) {
				columnId = base.getId();
				columnName = base.getName();
				columnJpName = base.getJpName();
				type = base.getType();
				comment = base.getComment();
				dispOrder = base.getDispOrder();
			}
			
			ColumnBuilder columnId(String value) {
				columnId = value;
				return this;
			}
			
			ColumnBuilder columnName(String value) {
				columnName = value;
				return this;
			}
			
			ColumnBuilder columnJpName(String value) {
				columnJpName = value;
				return this;
			}
			
			ColumnBuilder type(DefineColumnType value) {
				type = value;
				return this;
			}
			
			ColumnBuilder comment(String value) {
				comment = value;
				return this;
			}
			
			ColumnBuilder dispOrder(int value) {
				dispOrder = value;
				return this;
			}
			
			ColumnDesign build() {
				return new ColumnDesign(
						columnId, columnName, columnJpName, type, comment, dispOrder);
			}
		}
		
		static class Type{
			static DefineColumnType varchar(int length) {
				return new DefineColumnType(DataType.VARCHAR, length, 0, false, "", "");
			}
			
			static DefineColumnType real(int length, int scale) {
				return new DefineColumnType(DataType.REAL, length, scale, false, "", "");
			}
		}
	}
	
	static class Alteration{
		static final nts.uk.cnv.dom.td.alteration.Alteration BASE = create(
				Dummy.ALTER_ID, 
				Dummy.FEATURE_ID, 
				GeneralDateTime.now(),
				Dummy.TABLE_ID,
				Dummy.META, 
				Arrays.asList(new AddColumn(Column.BASE))
				);
		static nts.uk.cnv.dom.td.alteration.Alteration create(
				String alterId,
				String featureId,
				GeneralDateTime time,
				String tableId,
				AlterationMetaData metaData,
				List<AlterationContent> contents
				) {
			return new nts.uk.cnv.dom.td.alteration.Alteration(
					alterId,
					featureId,
					time,
					tableId,
					metaData,
					contents);
		}	
		static class Builder {
			String alterId = BASE.getAlterId();
			String featureId = BASE.getFeatureId(); 
			GeneralDateTime time = BASE.getCreatedAt();
			String tableId = BASE.getTableId();
			AlterationMetaData metaData = BASE.getMetaData();
			List<AlterationContent> contents = BASE.getContents();
			
			Builder changeAlterId(String altId){
				this.alterId = altId;
				return this;
			}
			Builder changeContens(AlterationContent altContent){
				this.contents = Arrays.asList(altContent);
				return this;
			}
			
			public nts.uk.cnv.dom.td.alteration.Alteration build() {
				return create(alterId, featureId, time, tableId, metaData, contents);
			}
		}
		static Builder builder() {
			return new Builder();
		}
	}
	
	static class TableSnapshot{
		static final nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot BASE = create(
				Dummy.TABLE_SNAPSHOT_ID,
				Table.BASE
		);
		static nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot create(String snapshotId, TableDesign tbDesign){
			return new nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot(snapshotId, tbDesign);
		}
		static class Builder {
			String snapshotId = BASE.getSnapshotId();
			TableDesign tdDesign = Table.BASE;
			Builder alterIdReplace(String snapshotId){
				return this;
			}
//			Builder replaceSnapshotId(String snapshotId) {
//				this.snapshotId = snapshotId;
//				return this;
//			}
			nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot build() {
				return create(snapshotId, tdDesign);
			}
		}
		static Builder builder() {
			return new Builder();
		}
	}
	
	static class Dummy {
		static final String ALTER_ID = "oruta";
		static final String TABLE_ID = "table";
		static final TableName TABLE_NAME = new TableName("ABCDE_TABLE");
		static final String TABLE_JP_NAME = "テーブル";
		static final String COMMENT = "comment";
		static final String TABLE_SNAPSHOT_ID = "tablesnapshot";
		static final String FEATURE_ID = "feature";
		static final AlterationMetaData META = new AlterationMetaData("user", COMMENT);
		static final String COLUMN_ID = "column";
		static final String COLUMN_NAME = "XYZ_COLUMN";
		static final String COLUMN_JP_NAME = "カラムン";
	}
	

}
