using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Oruta
{
    /// <summary>
    /// orutaへの問い合わせを抽象化したやつ
    /// </summary>
    class OrutaQuery
    {
        private OrutaSql sql = new OrutaSql();

        public OrutaTable Table(string tableName)
        {
            string snapshotId = LatestSnapshotId();

            var tableRow = sql.SelectOne($"select * from NEM_TD_SNAPSHOT_TABLE where SNAPSHOT_ID = '{snapshotId}' and NAME = '{tableName}'");
            string tableId = tableRow["TABLE_ID"] as string;
            var columnRows = sql.Select($"select * from NEM_TD_SNAPSHOT_COLUMN where SNAPSHOT_ID = '{snapshotId}' and TABLE_ID = '{tableId}'");

            return new OrutaTable(tableRow, columnRows);
        }

        private string LatestSnapshotId()
        {
            var row = sql.Select("select * from NEM_TD_SNAPSHOT_SCHEMA order by GENERATED_AT desc").First();
            return row["SNAPSHOT_ID"] as string;
        }
    }
}
