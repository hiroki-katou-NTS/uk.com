using System;
using System.Collections.Generic;
using System.Data;
using System.IO;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Linker;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Output
{
    /// <summary>
    /// �O���o�͂̌��ʃt�@�C����Smile���̃f�[�^�t�H�[�}�b�g�ɕϊ�
    /// </summary>
    public class SmileConverter
    {
        #region �O���o�͂̌��ʃt�@�C����Smile���̃f�[�^�t�H�[�}�b�g�ɕϊ�
        /// <summary>
        /// �O���o�͂̏o�͍���List����Smile���A�gDataTable�̃X�L�[�}���쐬����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <returns>Smile���A�gDataTable�̃X�L�[�}</returns>
        public DataTable CreateSmileDataTableSchema(TaskInfo TI)
        {
            #region �O���o�͂̏o�͍���List����Smile���A�gDataTable�̃X�L�[�}���쐬����
            // Smile���A�gDataTable
            DataTable dtSchema = new DataTable();

            foreach (string itemCode in TI.Output.Setting.ItemCodeList)
            {
                dtSchema.Columns.Add(itemCode.PadLeft(5, '0'), System.Type.GetType(DataType.String));
            }

            // Smile�ΑӘA�g�f�[�^���C�A�E�g���`�F�b�N(�K�{����)
            if (!dtSchema.Columns.Contains(SmileRequiredItem.EMPLOYEE_CD) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.YEAR) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.COMPANY_CD) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.MONTH))
            {
                TI.ErrorMsgList.Add("�A�g�f�[�^�t�H�[�}�b�g������������܂���B�Ύ��Y�|�O���o�͉�ʂ����m�F���������B");
                return null;
            }

            return dtSchema;
            #endregion
        }

        /// <summary>
        /// �O���o�͂̌��ʃt�@�C����ǂݍ��݁ASmile���A�gDataTable�ɒǉ�����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="dtSmile">Smile���A�gDataTable�̃X�L�[�}</param>
        /// <param name="intStart">�擾�J�n�C���f�b�N�X</param>
        /// <param name="intCnt">�擾����</param>
        /// <returns></returns>
        public void ReadOutputCsvFile(TaskInfo TI, DataTable dtSmile, int intStart, ref int intCnt)
        {
            #region �O���o�͂̌��ʃt�@�C����ǂݍ��݁ASmile���A�gDataTable�ɒǉ�����
            if (TI.Output.File == "" || !new FileInfo(TI.Output.File).Exists)
            {
                TI.ErrorMsgList.Add("�����Αӏ����擾�ł��܂���ł����B�ꎞ�I�ȃT�[�o�[�̖�肪�������Ă��܂��B" + Environment.NewLine + TI.Output.File);
                return;
            }

            try
            {
                #region �O���o�͌��ʃt�@�C���ǂݍ��ݏ���
                using (StreamReader sr = new StreamReader(TI.Output.File, Encode.Text))
                {
                    // 1�s�̃f�[�^
                    string line = null;
                    // �t�@�C���s��
                    int rowCount = 0;

                    while ((line = sr.ReadLine()) != null)
                    {
                        // �������o�͋敪
                        if (TI.Output.Setting.CondNameDisplay)
                        {
                            TI.Output.Setting.CondNameDisplay = false;
                            continue;
                        }
                        // ���ږ��o�͋敪
                        if (TI.Output.Setting.ItemNameDisplay)
                        {
                            TI.Output.Setting.ItemNameDisplay = false;
                            continue;
                        }

                        // �����f�[�^�����̃J�E���g
                        rowCount++;

                        // �J�n�C���f�b�N�X����f�[�^��ǂݍ���
                        if (intStart > rowCount)
                        {
                            continue;
                        }
                        // �擾������0���ɂȂ����ꍇ�͒��f����B
                        if (intCnt == 0)
                        {
                            break;
                        }

                        // 1�s�̍��ڔz��
                        string[] valueList = null;

                        #region ��؂蕶��
                        switch (TI.Output.Setting.Delimiter)
                        {
                            case Delimiter.Comma:
                                valueList = line.Split(',');
                                break;
                            case Delimiter.Semicolon:
                                valueList = line.Split(':');
                                break;
                            case Delimiter.Space:
                                valueList = line.Split(' ');
                                break;
                            case Delimiter.Tab:
                                valueList = line.Split('?');
                                break;
                            default:
                                break;
                        }
                        #endregion ��؂蕶��

                        if (line.ToString() != "") // �󔒍s�̓J�E���g���Ȃ�
                        {
                            DataRow row = dtSmile.NewRow();

                            for (int i = 0; i < valueList.Length; i++)
                            {
                                string value = valueList[i].ToString();

                                #region ������`��
                                switch (TI.Output.Setting.StringFormat)
                                {
                                    case StringFormat.None:
                                        break;
                                    case StringFormat.SingleQuotes:
                                        value = value.Replace("'", "");
                                        break;
                                    case StringFormat.DoubleQuotes:
                                        value = value.Replace("\"", "");
                                        break;
                                    default:
                                        break;
                                }
                                #endregion ������`��

                                row[i] = value;
                            }

                            dtSmile.Rows.Add(row);
                            intCnt--;
                        }
                    }
                }
                #endregion
            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("�O���o�͌��ʃt�@�C���ǂݍ��ݏ��������s���܂����B" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// �x�����敪�ɊY������ٗp���(�Ώ۔N��)�Ɋ�Â��A�ΏێЈ����i�荞��
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="dtSmile">Smile���A�gDataTable</param>
        public void FilterByPayEmployment(TaskInfo TI, DataTable dtSmile)
        {
            #region �x�����敪�ɊY������ٗp���(�Ώ۔N��)�Ɋ�Â��A�ΏێЈ����i�荞��
            // �o�͑Ώێ�List.
            List<OutputEmployeeInfo> targetList = new List<OutputEmployeeInfo>();

            // �x�����敪�̌ٗp�^���߁^�����N���^���ߊ��ԏ��
            foreach (MonthlyClosingEmployInfo monthlyClosingEmployInfo in TI.Output.MonthlyClosingEmployInfoList)
            {
                // ���Y�ٗp�E���ߊ��Ԃ̎Ј��𒊏o����

                // �ٗpCD
                string employmentCd = monthlyClosingEmployInfo.EmploymentCd;
                // �����N��
                int yearMonth = monthlyClosingEmployInfo.ProcessMonth;
                // ���ߊJ�n��
                DateTime startDate = monthlyClosingEmployInfo.StartDate;
                // ���ߏI����
                DateTime endDate = monthlyClosingEmployInfo.EndDate;

                // �w����ԂɎw��ٗp�ōݐE����Ј��ꗗ���擾���� (�ݐE�ҁE�x�Ǝ҂��Ώ�)
                string errMsg = "";
                SelectEmployeesByEmpParam paramEmployeesByEmp = new SelectEmployeesByEmpParam(employmentCd, startDate, endDate);
                List<string> employeeList = EmployeeLinker.SelectEmployeesByEmployment(TI, paramEmployeesByEmp, ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("�Y���ٗp�̎Ј��擾�����Ɏ��s���܂����B�B" + errMsg);
                    return;
                }

                foreach (string employeeCd in employeeList)
                {
                    #region ���ʎ��т̏��F��Ԃ̃`�F�b�N
                    if (TI.Setting.SmileOutputSetting.CheckApproveStatus)
                    {
                        // ���ʎ��т̒��ߏ����擾����
                        List<MonthlyClosureInfo> listMonthlyClosureInfo = ClosureLinker.GetMonthlyClosureInfo(TI, employeeCd, yearMonth, ref errMsg);
                        if (errMsg != "")
                        {
                            TI.ErrorMsgList.Add("���ʎ��т̒��ߏ��̎擾�����Ɏ��s���܂����B" + Environment.NewLine + errMsg);
                            return;
                        }
                        if (listMonthlyClosureInfo.Count > 0)
                        {
                            // �Y���Ј��̒��ߏ��
                            MonthlyClosureInfo monthlyClosureInfo = listMonthlyClosureInfo[0]; // ���߈�̂�

                            ApproveStatusCheckParam paramStatusCheck = new ApproveStatusCheckParam();

                            paramStatusCheck.employeeCd = employeeCd;
                            paramStatusCheck.startDate = startDate;
                            paramStatusCheck.endDate = endDate;
                            paramStatusCheck.yearMonth = yearMonth;
                            paramStatusCheck.closureID = monthlyClosureInfo.ClosureId;
                            paramStatusCheck.closureDay = monthlyClosureInfo.ClosureDay;
                            paramStatusCheck.lastDayOfMonth = monthlyClosureInfo.isLastDay;
                            paramStatusCheck.baseDate = endDate; // ����͒��ߏI����

                            bool monthlyApprove = MonthlyCheckLinker.ApproveStatusCheck(TI, paramStatusCheck, ref errMsg);
                            if (errMsg != "")
                            {
                                TI.ErrorMsgList.Add("���ʎ��т̏��F��Ԃ̃`�F�b�N�����Ɏ��s���܂����B" + Environment.NewLine + errMsg);
                                return;
                            }
                            if (!monthlyApprove)
                            {
                                TI.ErrorMsgList.Add("���ʎ��тɖ��m�F�̃f�[�^�����݂��܂��B�Ύ��Y�ɂĂ��m�F���������B" + Environment.NewLine + "�ΏێЈ�CD�F" + employeeCd);
                                return;
                            }
                        }
                    }
                    #endregion ���ʎ��т̏��F��Ԃ̃`�F�b�N

                    targetList.Add(new OutputEmployeeInfo(employeeCd, monthlyClosingEmployInfo.ProcessMonth.ToString()));
                }
            }

            DataTable dtWork = dtSmile.Copy();
            dtSmile.Clear();

            foreach (DataRow drWork in dtWork.Rows)
            {
                // �Ј�CD
                string employeeCd = drWork[SmileRequiredItem.EMPLOYEE_CD].ToString();
                // �����N��(�N�E���ɂ͓������N���ŏo�͂����)
                string processMonth = drWork[SmileRequiredItem.YEAR].ToString();

                OutputEmployeeInfo employeeInfo = new OutputEmployeeInfo(employeeCd, processMonth);

                if (targetList.Contains(employeeInfo))
                {
                    dtSmile.ImportRow(drWork);
                }
            }
            #endregion
        }

        /// <summary>
        /// Smile���̃f�[�^�t�H�[�}�b�g�ɍ��킹�āA�o�̓f�[�^�𒲐�����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="dtSmile">Smile���A�gDataTable</param>
        public void ReadyforSmileData(TaskInfo TI, DataTable dtSmile)
        {
            #region Smile���̃f�[�^�t�H�[�}�b�g�ɍ��킹�āA�o�̓f�[�^�𒲐�����
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // �Ј�CD *6���֒��� TODO
                drSmile[SmileRequiredItem.EMPLOYEE_CD] = drSmile[SmileRequiredItem.EMPLOYEE_CD].ToString().Substring(2, 6);

                // �N
                drSmile[SmileRequiredItem.YEAR] = TI.Output.SmileYear;

                // ���CD *�Œ�y1�z
                drSmile[SmileRequiredItem.COMPANY_CD] = "1";

                // ��
                drSmile[SmileRequiredItem.MONTH] = TI.Output.SmileMonth;
            }
            #endregion
        }
        #endregion
    }

    /// <summary>
    /// Smile�ΑӘA�g�f�[�^���C�A�E�g�̕K�{����
    /// </summary>
    public class SmileRequiredItem
    {
        #region Smile�ΑӘA�g�f�[�^���C�A�E�g�̕K�{����
        /// <summary>
        /// Smile �Ј�CD
        /// </summary>
        public const string EMPLOYEE_CD = "00001";
        /// <summary>
        /// Smile �����N
        /// </summary>
        public const string YEAR = "00002";
        /// <summary>
        /// Smile ���CD
        /// </summary>
        public const string COMPANY_CD = "00003";
        /// <summary>
        /// Smile ������
        /// </summary>
        public const string MONTH = "00004";
        #endregion
    }
}
