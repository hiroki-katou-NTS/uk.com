using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using UKExportTool.Output;

namespace UKExportTool.Join
{
    class JoinTable
    {
        private readonly int index;
        private readonly string tableName;
        private readonly List<IOnItem> onItems;

        public int Index { get { return index; } }

        public string TableName { get { return tableName; } }

        public JoinTable(int index, string definition, Func<int, JoinTable> getJoin)
        {
            this.index = index;

            var defParts = definition.Split('/');
            tableName = defParts[0].Trim();

            onItems = defParts[1].Split(',')
                .SelectMany(s => BuildOnItem(s, index, getJoin))
                .ToList();
        }

        private static List<IOnItem> BuildOnItem(string definition, int joinIndex, Func<int, JoinTable> getJoin)
        {
            definition = definition.Trim();

            string refSymbol = definition.Substring(1);
            int? refJoinIndex = TryGetJoinIndex(refSymbol);
            if (refJoinIndex.HasValue)
            {
                var refJoin = getJoin(refJoinIndex.Value);
                return refJoin.onItems.Select(o => o.Refer(Alias.Join(joinIndex))).ToList();
            }

            IOnItem onItem;

            var equalsParts = definition.Split('=').Select(s => s.Trim()).ToList();
            if (equalsParts.Count == 2)
            {
                onItem = new ColumnToValueOnItem(equalsParts[0], equalsParts[1]);
            }
            else
            {
                onItem = ColumnToColumnOnItem.Same(definition, joinIndex);
            }
            
            return new List<IOnItem>(new[] { onItem });
        }

        public static int? TryGetJoinIndex(string text)
        {
            var result = Regex.Match(text.Trim().ToLower(), @"#?join(\d+)");
            if (result.Groups.Count == 2)
            {
                return int.Parse(result.Groups[1].Value);
            }

            return null;
        }

        public string ToSql()
        {
            string on = string.Join(" and ", onItems.Select(o => o.ToSql()));
            return $"left join {tableName} as {Alias.Join(index)} on {on}";
        }
    }
}
