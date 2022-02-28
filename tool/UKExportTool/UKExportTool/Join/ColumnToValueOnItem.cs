using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Join
{
    class ColumnToValueOnItem : IOnItem
    {
        private readonly string columnName;
        private readonly string value;

        public ColumnToValueOnItem(string columnName, string value)
        {
            this.columnName = columnName;
            this.value = value;
        }

        public IOnItem Refer(string newRightTable)
        {
            return this;
        }

        public string ToSql()
        {
            return $"{columnName} = {value}";
        }
    }
}
