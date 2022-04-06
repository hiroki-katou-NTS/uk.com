using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// �O�����-�E�����J�e�S��
    /// </summary>
    public class EmployeeInfo : AcceptCategoryBase
    {
        public EmployeeInfo() { }

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
                if (drSmile[EmployeeInfoItem.END_DAY].ToString() == "99999999")
                {
                    drSmile[EmployeeInfoItem.END_DAY] = new DateTime(9999, 12, 31).ToString(Format.Date);
                }

                /*
                 * �yTrim���K�v�ȍ��ځz 8�������̏ꍇ�ɁA���X�y�[�X�Ŗ��߂Ă���
                 * �Ј�CD
                 * �g�D�R�[�h�A�����R�[�h
                 * ���ނO�`���ނX
                */

                // �Ј��R�[�h
                drSmile[EmployeeInfoItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.EMPLOYEE_CD]);

                // �g�D�R�[�h
                drSmile[EmployeeInfoItem.ORGANIZATION_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.ORGANIZATION_CD]);

                // �����R�[�h
                drSmile[EmployeeInfoItem.BELONGS_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.BELONGS_CD]);

                // ���ނO
                drSmile[EmployeeInfoItem.CLASSIFICATION_0] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_0]);

                // ���ނP
                drSmile[EmployeeInfoItem.CLASSIFICATION_1] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_1]);

                // ���ނQ
                drSmile[EmployeeInfoItem.CLASSIFICATION_2] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_2]);

                // ���ނR
                drSmile[EmployeeInfoItem.CLASSIFICATION_3] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_3]);

                // ���ނS
                drSmile[EmployeeInfoItem.CLASSIFICATION_4] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_4]);

                // ���ނT
                drSmile[EmployeeInfoItem.CLASSIFICATION_5] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_5]);

                // ���ނU
                drSmile[EmployeeInfoItem.CLASSIFICATION_6] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_6]);

                // ���ނV
                drSmile[EmployeeInfoItem.CLASSIFICATION_7] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_7]);

                // ���ނW
                drSmile[EmployeeInfoItem.CLASSIFICATION_8] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_8]);

                // ���ނX
                drSmile[EmployeeInfoItem.CLASSIFICATION_9] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_9]);
            }
            #endregion
        }
    }

    /// <summary>
    /// �E�����J�e�S��-���ڒ�`
    /// </summary>
    public class EmployeeInfoItem
    {
        #region �E�����J�e�S��-���ڒ�`
        /// <summary>
        /// �Ј��R�[�h
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// ���ߔN����
        /// </summary>
        public const string ANNOUNCE_DAY = "002";

        /// <summary>
        /// �I���N����
        /// </summary>
        public const string END_DAY = "004";

        /// <summary>
        /// �g�D�R�[�h
        /// </summary>
        public const string ORGANIZATION_CD = "007";

        /// <summary>
        /// �����R�[�h
        /// </summary>
        public const string BELONGS_CD = "040";

        /// <summary>
        /// ���ނO
        /// </summary>
        public const string CLASSIFICATION_0 = "008";

        /// <summary>
        /// ���ނP
        /// </summary>
        public const string CLASSIFICATION_1 = "009";

        /// <summary>
        /// ���ނQ
        /// </summary>
        public const string CLASSIFICATION_2 = "010";

        /// <summary>
        /// ���ނR
        /// </summary>
        public const string CLASSIFICATION_3 = "011";

        /// <summary>
        /// ���ނS
        /// </summary>
        public const string CLASSIFICATION_4 = "012";

        /// <summary>
        /// ���ނT
        /// </summary>
        public const string CLASSIFICATION_5 = "013";

        /// <summary>
        /// ���ނU
        /// </summary>
        public const string CLASSIFICATION_6 = "014";

        /// <summary>
        /// ���ނV
        /// </summary>
        public const string CLASSIFICATION_7 = "015";

        /// <summary>
        /// ���ނW
        /// </summary>
        public const string CLASSIFICATION_8 = "016";

        /// <summary>
        /// ���ނX
        /// </summary>
        public const string CLASSIFICATION_9 = "017";

        #endregion �E�����J�e�S��-���ڒ�`
    }
}
