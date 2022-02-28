using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UKExportTool.Join;
using UKExportTool.Output;

namespace UKExportTool
{
    /// <summary>
    /// 設定ファイルを解析する
    /// </summary>
    class Setting
    {
        public readonly int categoryId = 0;
        public readonly string mainTableName = null;
        public readonly bool isSyain = false;
        public readonly JoinTables joins = new JoinTables();

        public Setting(string path)
        {
            List<string> lines = File.ReadAllLines(path, Encoding.UTF8).ToList();

            foreach (var line in lines)
            {
                var parts = line.Split(':').Select(s => s.Trim()).ToList();
                string key = parts[0].ToLower();
                string value = parts.Count == 1 ? null : parts[1];

                switch (key)
                {
                    case "categoryid":
                        categoryId = int.Parse(value);
                        continue;
                    case "maintable":
                        mainTableName = value;
                        continue;
                    case "syain":
                        isSyain = true;
                        continue;
                }

                int? joinIndex = JoinTable.TryGetJoinIndex(key);
                if (joinIndex.HasValue)
                {
                    joins.Add(joinIndex.Value, value);
                }
            }

            if (categoryId == 0)
            {
                throw new Exception("CategoryIdがありません");
            }
            if (mainTableName == null)
            {
                throw new Exception("MaibTableがありません");
            }
        }

    }
}
