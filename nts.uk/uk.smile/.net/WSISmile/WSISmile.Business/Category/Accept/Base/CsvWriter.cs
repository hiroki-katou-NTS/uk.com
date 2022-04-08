using System;
using System.Data;
using System.Text;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// CSV�t�@�C����������
    /// </summary>
    public class CsvWriter
    {
        /// <summary>
        /// DataTable�^�ɓ����Ă���f�[�^�̃J���������ACSV�`���̕�����ɕϊ����ĕԂ�
        /// </summary>
        /// <param name="dataTable">�ϊ����f�[�^(DataTable�^�I�u�W�F�N�g)</param>
        /// <returns>�ϊ���f�[�^(CSV�`���̕�����)</returns>
        public static string ToCsvString_Header(DataTable dataTable)
        {
            #region DataTable�^�ɓ����Ă���f�[�^�̃J���������ACSV�`���̕�����ɕϊ����ĕԂ�
            string str = "";
            StringBuilder sbCsv = new StringBuilder();

            // �J��������CSV�`��������֒ǉ�
            for (int i = 0; i < dataTable.Columns.Count; i++)
            {
                str = dataTable.Columns[i].ColumnName;
                str = TransCsvCell(str);

                if (sbCsv.Length != 0)
                {
                    sbCsv.Append(",");
                }

                sbCsv.Append(str);
            }

            sbCsv.Append("\r\n");

            return sbCsv.ToString();
            #endregion
        }

        /// <summary>
        /// DataTable�^�ɓ����Ă���f�[�^(�f�[�^�̂�)���ACSV�`���̕�����ɕϊ����ĕԂ�
        /// </summary>
        /// <param name="dataTable">�ϊ����f�[�^(DataTable�^�I�u�W�F�N�g)</param>
        /// <returns>�ϊ���f�[�^(CSV�`���̕�����)</returns>
        public static string ToCsvString_Data(DataTable dataTable)
        {
            #region DataTable�^�ɓ����Ă���f�[�^(�f�[�^�̂�)���ACSV�`���̕�����ɕϊ����ĕԂ�
            string str = string.Empty;
            StringBuilder sbCsv = new StringBuilder();

            StringBuilder sbLine = null;

            // �e�[�u���̊e�f�[�^��CSV�`��������֒ǉ�
            foreach (DataRow aRow in dataTable.Rows)
            {
                sbLine = new StringBuilder();

                for (int i = 0; i < dataTable.Columns.Count; i++)
                {
                    str = aRow[i].ToString();
                    str = TransCsvCell(str);

                    if (sbLine.Length != 0)
                    {
                        sbLine.Append(",");
                    }
                    sbLine.Append(str);
                }

                sbCsv.Append(sbLine.ToString());

                sbCsv.Append("\r\n");
            }

            return sbCsv.ToString();
            #endregion
        }

        /// <summary>
        /// �P�̕������CSV�`���o�͗p�ɕϊ����� �@
        /// </summary>
        /// <param name="str">������</param>
        /// <returns>CSV�`���o�͗p�ɕϊ�����������</returns>
        public static string TransCsvCell(string str)
        {
            #region �P�̕������CSV�`���o�͗p�ɕϊ�����
            /*
             * �@�u"�v���u""�v�֕ϊ�����
             * �A��������u"�v�ň͂�
            */

            return "\"" + str.Replace("\"", "\"\"") + "\"";
            #endregion
        }
    }
}
