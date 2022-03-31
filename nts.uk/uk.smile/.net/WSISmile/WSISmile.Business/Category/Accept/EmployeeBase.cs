using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// �O�����-�l����{���J�e�S��
    /// </summary>
    public class EmployeeBase : AcceptCategoryBase
    {
        #region �O�����-�l����{���J�e�S��
        public EmployeeBase() { }

        /// <summary>
        /// Smile�������������A�g�f�[�^��ҏW
        /// </summary>
        /// <param name="dtSmile">Smile���̘A�g�f�[�^</param>
        public override void SmileDataEditing(DataTable dtSmile)
        {
            #region Smile�������������A�g�f�[�^��ҏW : SmileDataEditing
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // �t���K�i *�S�p���K�v
                drSmile[EmployeeBaseItem.KANA_NAME] =
                    Microsoft.VisualBasic.Strings.StrConv(drSmile[EmployeeBaseItem.KANA_NAME].ToString().Trim(), Microsoft.VisualBasic.VbStrConv.Wide, 0);

                /*
                 * �yTrim���K�v�ȍ��ځz 8�������̏ꍇ�ɁA���X�y�[�X�Ŗ��߂Ă���
                 * �Ј�CD
                 * �g�D�R�[�h�A�����R�[�h
                 * ���ނO�`���ނX
                */

                // �Ј��R�[�h
                drSmile[EmployeeBaseItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.EMPLOYEE_CD]);

                // �ސE�N����
                if (Toolbox.IsNull(drSmile[EmployeeBaseItem.RETIREMENT_DATE]) || decimal.Parse(drSmile[EmployeeBaseItem.RETIREMENT_DATE].ToString()) == 0m)
                {
                    drSmile[EmployeeBaseItem.RETIREMENT_DATE] = 99991231;
                }

                // �g�D�R�[�h
                drSmile[EmployeeBaseItem.ORGANIZATION_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.ORGANIZATION_CD]);

                // �����R�[�h
                drSmile[EmployeeBaseItem.BELONGS_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.BELONGS_CD]);

                // ���ނO
                drSmile[EmployeeBaseItem.CLASSIFICATION_0] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_0]);

                // ���ނP
                drSmile[EmployeeBaseItem.CLASSIFICATION_1] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_1]);

                // ���ނQ
                drSmile[EmployeeBaseItem.CLASSIFICATION_2] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_2]);

                // ���ނR
                drSmile[EmployeeBaseItem.CLASSIFICATION_3] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_3]);

                // ���ނS
                drSmile[EmployeeBaseItem.CLASSIFICATION_4] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_4]);

                // ���ނT
                drSmile[EmployeeBaseItem.CLASSIFICATION_5] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_5]);

                // ���ނU
                drSmile[EmployeeBaseItem.CLASSIFICATION_6] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_6]);

                // ���ނV
                drSmile[EmployeeBaseItem.CLASSIFICATION_7] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_7]);

                // ���ނW
                drSmile[EmployeeBaseItem.CLASSIFICATION_8] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_8]);

                // ���ނX
                drSmile[EmployeeBaseItem.CLASSIFICATION_9] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_9]);
            }
            #endregion

            #region *** �d���ƂȂ����폜 ***
            // ���� ���d��
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME);
            }
            // ���� ���d��
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_SIMPLE))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_SIMPLE);
            }
            // �t���K�i ���d��
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_FURIGANA))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_FURIGANA);
            }
            // ���[�}���� ���d��
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_ROMA))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_ROMA);
            }
            #endregion *** �d���ƂȂ����폜 ***
        }

        #endregion �O�����-�l����{���J�e�S��
    }

    /// <summary>
    /// �l����{���J�e�S��-���ڒ�`
    /// </summary>
    public class EmployeeBaseItem
    {
        #region �l����{���J�e�S��-���ڒ�`
        /// <summary>
        /// �Ј��R�[�h
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// �Ј����J�i
        /// </summary>
        public const string KANA_NAME = "004";

        /// <summary>
        /// �ސE�N����
        /// </summary>
        public const string RETIREMENT_DATE = "033";

        /// <summary>
        /// �g�D�R�[�h
        /// </summary>
        public const string ORGANIZATION_CD = "059";

        /// <summary>
        /// �����R�[�h
        /// </summary>
        public const string BELONGS_CD = "060";

        /// <summary>
        /// ���ނO
        /// </summary>
        public const string CLASSIFICATION_0 = "061";

        /// <summary>
        /// ���ނP
        /// </summary>
        public const string CLASSIFICATION_1 = "062";

        /// <summary>
        /// ���ނQ
        /// </summary>
        public const string CLASSIFICATION_2 = "063";

        /// <summary>
        /// ���ނR
        /// </summary>
        public const string CLASSIFICATION_3 = "064";

        /// <summary>
        /// ���ނS
        /// </summary>
        public const string CLASSIFICATION_4 = "065";

        /// <summary>
        /// ���ނT
        /// </summary>
        public const string CLASSIFICATION_5 = "066";

        /// <summary>
        /// ���ނU
        /// </summary>
        public const string CLASSIFICATION_6 = "067";

        /// <summary>
        /// ���ނV
        /// </summary>
        public const string CLASSIFICATION_7 = "068";

        /// <summary>
        /// ���ނW
        /// </summary>
        public const string CLASSIFICATION_8 = "069";

        /// <summary>
        /// ���ނX
        /// </summary>
        public const string CLASSIFICATION_9 = "070";

        #endregion �l����{���J�e�S��-���ڒ�`
    }

    /// <summary>
    /// �l����{���J�e�S��-���ڒ�` ���d��
    /// </summary>
    public class EmployeeBaseItemDuplicate
    {
        #region �l����{���J�e�S��-���ڒ�` ���d��
        /// <summary>
        /// ���� ���d��
        /// </summary>
        public const string NAME = "011";

        /// <summary>
        /// ���� ���d��
        /// </summary>
        public const string NAME_SIMPLE = "012";

        /// <summary>
        /// �t���K�i ���d��
        /// </summary>
        public const string NAME_FURIGANA = "013";

        /// <summary>
        /// ���[�}���� ���d��
        /// </summary>
        public const string NAME_ROMA = "014";
        #endregion �l����{���J�e�S��-���ڒ�` ���d��
    }
}
