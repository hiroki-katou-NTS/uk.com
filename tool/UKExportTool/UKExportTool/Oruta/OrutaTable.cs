using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Oruta
{
    /// <summary>
    /// orutaから取得したテーブル設計情報
    /// </summary>
    class OrutaTable
    {
        public readonly string name;
        public readonly string nameJp;
        public readonly List<OrutaColumn> columns;

        public OrutaTable(DataRow tableRow, IEnumerable<DataRow> columnRows)
        {
            name = tableRow["NAME"] as string;
            nameJp = tableRow["JPNAME"] as string;
            columns = columnRows.Select(r => new OrutaColumn(r)).ToList();
        }
    }

    class OrutaColumn
    {
        public readonly string id;
        public readonly string name;
        public readonly string nameJp;
        public readonly string dataType;

        public OrutaColumn(DataRow row)
        {
            id = row["ID"] as string;
            name = row["NAME"] as string;
            nameJp = row["JPNAME"] as string;
            dataType = row["DATA_TYPE"] as string;
        }
    }
}
