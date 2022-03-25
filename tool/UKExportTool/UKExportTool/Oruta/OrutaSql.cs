using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Oruta
{
    /// <summary>
    /// orutaのDBに対してSQLでアクセスする
    /// </summary>
    class OrutaSql
    {
        private readonly SqlConnection connection;
        private readonly SqlDataAdapter adapter;

        public OrutaSql()
        {
            connection = new SqlConnection()
            {
                ConnectionString = @"Data Source=192.168.50.14;Initial Catalog=UK_ORUTA;User Id=sa;Password=kinjirou;",
            };

            adapter = new SqlDataAdapter();
        }

        public IEnumerable<DataRow> Select(string selectSql)
        {
            SqlCommand command = new SqlCommand()
            {
                Connection = connection,
                CommandText = selectSql,
            };

            var ds = new DataSet();
            adapter.SelectCommand = command;
            adapter.Fill(ds);

            return ds.Tables[0].Rows.OfType<DataRow>();
        }

        public DataRow SelectOne(string selectSql)
        {
            return Select(selectSql).First();
        }
    }
}
