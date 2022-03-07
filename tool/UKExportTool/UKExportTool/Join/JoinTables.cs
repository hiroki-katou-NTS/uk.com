using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Join
{
    class JoinTables : IEnumerable<JoinTable>
    {
        private readonly List<JoinTable> joins = new List<JoinTable>();

        public void Add(int index, string definition)
        {
            var join = new JoinTable(index, definition, FindByIndex);
            joins.Add(join);
        }

        private JoinTable FindByIndex(int index)
        {
            return joins.Where(j => j.Index == index).First();
        }

        public string ToSql()
        {
            return string.Join(" ", joins.Select(j => j.ToSql()));
        }

        public IEnumerator<JoinTable> GetEnumerator()
        {
            return joins.GetEnumerator();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public bool IsEmpty { get { return joins.Count == 0; } }
    }
}
