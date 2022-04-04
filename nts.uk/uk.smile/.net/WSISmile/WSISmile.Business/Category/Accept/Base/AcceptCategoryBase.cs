using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;

using WSISmile.Business.Common;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// �O�����-���N���X
    /// </summary>
    public class AcceptCategoryBase
    {
        /// <summary>
        /// Smile�������������A�g�f�[�^��ҏW
        /// </summary>
        /// <param name="dtSmile">Smile���̘A�g�f�[�^</param>
        public virtual void SmileDataEditing(DataTable dtSmile) { }

        /// <summary>
        /// Smile���̘A�g�f�[�^���_��P�ʃt�H���_�ɂɕۑ�(CSV�t�@�C���ɏ�������)
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="data">Smile�������������A�g�f�[�^</param>
        /// <param name="category">����J�e�S��</param>
        /// <param name="append">�V�K:false/�ǉ�:true</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public void AcceptFileCreate(TaskInfo TI, DataTable data, string category, bool append, ref string errorMsg)
        {
            #region Smile���̘A�g�f�[�^���_��P�ʃt�H���_�ɂɕۑ� : AcceptFileCreate
            // �_��P�ʃt�H���_�Ɏ���Ώۃt�@�C����ۑ�����
            if (!append) // �V�K�̏ꍇ
            {
                TI.Accept.File = Path.Combine(TI.Contract.Folder, string.Format(category + "_{0}.csv", DateTime.Now.ToString(Format.DateAndTime)));
            }

            try
            {
                using (StreamWriter streamWriter = new StreamWriter(TI.Accept.File, append, Encode.Shift_JIS))
                {
                    if (!append) // �V�K�̏ꍇ
                    {
                        // Csv Header�������o��
                        streamWriter.Write(CsvWriter.ToCsvString_Header(data));
                        // Csv Data�������o��
                        streamWriter.Write(CsvWriter.ToCsvString_Data(data));
                    }
                    else
                    {
                        // Csv Data�������o��
                        streamWriter.Write(CsvWriter.ToCsvString_Data(data));
                    }
                }
            }
            catch
            {
                TI.Accept.File = "";
            }
            #endregion
        }
    }
}
