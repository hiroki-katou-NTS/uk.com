using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// �O�����-�x�E���J�e�S��
    /// </summary>
    public class EmployeeSuspension : AcceptCategoryBase
    {
        public EmployeeSuspension() { }

        /// <summary>
        /// Smile�������������A�g�f�[�^��ҏW
        /// </summary>
        /// <param name="dtSmile">Smile���̘A�g�f�[�^</param>
        public override void SmileDataEditing(DataTable dtSmile)
        {
            #region Smile�������������A�g�f�[�^��ҏW : SmileDataEditing
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // �I���N����
                if (drSmile[EmployeeSuspensionItem.END_DAY].ToString() == "99999999")
                {
                    drSmile[EmployeeSuspensionItem.END_DAY] = new DateTime(9999, 12, 31).ToString(Format.Date);
                }

                /*
                 * �yTrim���K�v�ȍ��ځz 8�������̏ꍇ�ɁA���X�y�[�X�Ŗ��߂Ă���
                 * �Ј�CD
                 * ���R�R�[�h
                */

                // �Ј��R�[�h
                drSmile[EmployeeSuspensionItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeSuspensionItem.EMPLOYEE_CD]);

                // ���R�R�[�h
                drSmile[EmployeeSuspensionItem.REASON_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeSuspensionItem.REASON_CD]);
            }
            #endregion
        }
    }

    /// <summary>
    /// �x�E���J�e�S��-���ڒ�`
    /// </summary>
    public class EmployeeSuspensionItem
    {
        #region �x�E���J�e�S��-���ڒ�`
        /// <summary>
        /// �Ј��R�[�h
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// �J�n�N����
        /// </summary>
        public const string START_DAY = "002";

        /// <summary>
        /// �I���N����
        /// </summary>
        public const string END_DAY = "005";

        /// <summary>
        /// ���R�R�[�h
        /// </summary>
        public const string REASON_CD = "008";

        /// <summary>
        /// �x�E���R
        /// </summary>
        public const string REASON = "009";
        #endregion �x�E���J�e�S��-���ڒ�`
    }
}
