using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Output
{
    class OiomtExOutLinkTable
    {
        string contractCode = "000000000000";
        int ctgId;
        string mainTable;
        string from1;
        string from2;
        string conditions = null;
        string outCondItemName1 = "";
        string outCondItemName2 = "";
        string outCondItemName3 = "";
        string outCondItemName4 = "";
        string outCondItemName5 = "";
        string outCondItemName6 = "";
        string outCondItemName7 = "";
        string outCondItemName8 = "";
        string outCondItemName9 = "";
        string outCondItemName10 = "";
        int? outCondAssociation1 = null;
        int? outCondAssociation2 = null;
        int? outCondAssociation3 = null;
        int? outCondAssociation4 = null;
        int? outCondAssociation5 = null;
        int? outCondAssociation6 = null;
        int? outCondAssociation7 = null;
        int? outCondAssociation8 = null;
        int? outCondAssociation9 = null;
        int? outCondAssociation10 = null;

        public OiomtExOutLinkTable(Setting setting)
        {
            ctgId = setting.categoryId;
            mainTable = setting.mainTableName;
            from1 = $"from {mainTable} as {Alias.Main}";
            from2 = ToSqlJoin(setting);
        }

        private static string ToSqlJoin(Setting setting)
        {
            var sql = new List<string>();
            if (setting.isSyain) sql.Add($"inner join BSYMT_SYAIN as SYA on {Alias.Main}.SID = SYA.SID inner join BPSMT_PERSON as PER on SYA.PID = PER.PID");
            if (!setting.joins.IsEmpty) sql.Add(setting.joins.ToSql());

            return string.Join(" ", sql);
        }

        public string ToCsvRecord()
        {
            return Csv.ToCsvLine(new object[]
            {
                ctgId,
                mainTable,
                from1,
                from2,
                conditions,
                outCondItemName1,
                outCondItemName2,
                outCondItemName3,
                outCondItemName4,
                outCondItemName5,
                outCondItemName6,
                outCondItemName7,
                outCondItemName8,
                outCondItemName9,
                outCondItemName10,
                outCondAssociation1,
                outCondAssociation2,
                outCondAssociation3,
                outCondAssociation4,
                outCondAssociation5,
                outCondAssociation6,
                outCondAssociation7,
                outCondAssociation8,
                outCondAssociation9,
                outCondAssociation10,
                contractCode,
            });
        }

    }
}
