using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Join
{
    interface IOnItem
    {
        string ToSql();

        IOnItem Refer(string newRightTable);
    }
}
