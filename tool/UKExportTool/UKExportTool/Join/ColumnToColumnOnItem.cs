using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UKExportTool.Output;

namespace UKExportTool.Join
{
    class ColumnToColumnOnItem : IOnItem
    {
        private readonly OnItemColumn left;
        private readonly OnItemColumn right;

        private ColumnToColumnOnItem(OnItemColumn left, OnItemColumn right)
        {
            this.left = left;
            this.right = right;
        }

        public static IOnItem Same(string column, int joinIndex)
        {
            return new ColumnToColumnOnItem(
                OnItemColumn.MainTable(column),
                OnItemColumn.Join(joinIndex, column));
        }

        public static IOnItem Different(string left, string right)
        {
            return new ColumnToColumnOnItem(
                OnItemColumn.Simple(left),
                OnItemColumn.Simple(right));
        }

        public static List<IOnItem> Refer(List<IOnItem> refOnItems, int joinIndex)
        {
            return refOnItems.Select(r => r.Refer(Alias.Join(joinIndex))).ToList();
        }

        public string ToSql()
        {
            return $"{left} = {right}";
        }

        public IOnItem Refer(string newRightTable)
        {
            return new ColumnToColumnOnItem(left, new OnItemColumn(newRightTable, right.column));
        }
    }

    class OnItemColumn
    {
        public readonly string table;
        public readonly string column;

        public OnItemColumn(string table, string column)
        {
            this.table = table;
            this.column = column;
        }

        public static OnItemColumn Simple(string column)
        {
            return new OnItemColumn(null, column);
        }

        public static OnItemColumn MainTable(string column)
        {
            return new OnItemColumn(Alias.Main, column);
        }

        public static OnItemColumn Join(int joinIndex, string column)
        {
            return new OnItemColumn(Alias.Join(joinIndex), column);
        }

        public override string ToString()
        {
            if (table == null)
            {
                return column;
            }

            return table + "." + column;
        }
    }
}
