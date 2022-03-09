using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using UKExportTool.Oruta;

namespace UKExportTool.Output
{
    class OiomtExOutCtgItemDt
    {
        public readonly string contractCd = "000000000000";
        public readonly int ctgId;
        public readonly string tblAlias;
        public readonly string tableName;
        public readonly string displayTableName;
        public readonly int ctgItemNo;
        public readonly string itemName;
        public readonly string fieldName;
        public readonly int dataType;
        public readonly int primaryKeyCls = 0;
        public readonly int displayCls = 1;
        public readonly int? dateCls = null;
        public readonly int requiredCategory = 0;
        public readonly int specialItem = 0;
        public readonly string searchValueCd = "";
        public readonly int keywordAtr = 0;

        public OiomtExOutCtgItemDt(Setting setting, OrutaTable table, string tableAlias, OrutaColumn column, int itemNo)
        {
            ctgId = setting.categoryId;
            tableName = table.name;
            tblAlias = tableAlias;
            displayTableName = table.nameJp;
            ctgItemNo = itemNo;
            itemName = column.nameJp;
            fieldName = column.name;
            dataType = ItemDataType.Infer(column).value;
        }

        public static List<OiomtExOutCtgItemDt> Load(Setting setting, OrutaQuery oruta)
        {
            var results = new List<OiomtExOutCtgItemDt>();
            int itemNo = 1;

            var mainTable = oruta.Table(setting.mainTableName);
            foreach (var column in mainTable.columns.OrderBy(c => InsertZeroIntoNumberSuffix(c)))
            {
                results.Add(new OiomtExOutCtgItemDt(setting, mainTable, Alias.Main, column, itemNo));
                itemNo++;
            }

            foreach (var join in setting.joins)
            {
                var table = oruta.Table(join.TableName);
                foreach (var column in mainTable.columns.OrderBy(c => InsertZeroIntoNumberSuffix(c)))
                {
                    results.Add(new OiomtExOutCtgItemDt(setting, table, Alias.Join(join.Index), column, itemNo));
                    itemNo++;
                }
            }

            return results;
        }

        /// <summary>
        /// カラムのソートと不要列の除外
        /// </summary>
        /// <param name="columns"></param>
        /// <returns></returns>
        private static List<OrutaColumn> ProcessColumns(List<OrutaColumn> columns)
        {
            var exclusions = new[] { "CONTRACT_CD", "CID" };
            var filtered = columns.Where(c => !exclusions.Contains(c.name));

            return filtered.OrderBy(c => InsertZeroIntoNumberSuffix(c)).ToList();
        }

        private static string InsertZeroIntoNumberSuffix(OrutaColumn column)
        {
            string itemName = column.nameJp;
            var match = Regex.Match(itemName, @"^(.+)(\d+)$");
            if (match.Groups.Count == 3)
            {
                string name = match.Groups[1].Value;
                int number = int.Parse(match.Groups[2].Value);
                return $"{name}{number:0000}";
            }

            return itemName;
        }

        public string ToCsvRecord()
        {
            return Csv.ToCsvLine(new object[]
            {
                tblAlias,
                ctgId,
                dataType,
                tableName,
                fieldName,
                primaryKeyCls,
                dateCls,
                specialItem,
                displayTableName,
                displayCls,
                ctgItemNo,
                itemName,
                requiredCategory,
                searchValueCd,
                keywordAtr,
                contractCd,
            });
        }
    }
}
