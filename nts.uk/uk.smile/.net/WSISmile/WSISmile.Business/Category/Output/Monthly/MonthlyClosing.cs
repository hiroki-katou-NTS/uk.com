using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Linker;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Link.Parameter.Setting;
using WSISmile.Business.Log;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Output
{
    /// <summary>
    /// �������ߏ����֘A
    /// </summary>
    public class MonthlyClosing
    {
        public MonthlyClosing() { }

        /// <summary>
        /// �x�����敪�ɊY������ٗp�^���߁^�����N���^���ߊ��ԏ�񃊃X�g���擾����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <returns></returns>
        public List<MonthlyClosingEmployInfo> LoadMonthlyClosingEmployInfo(TaskInfo TI)
        {
            #region �x�����敪�ɊY������ٗp�^���߁^�����N���^���ߊ��ԏ�񃊃X�g���擾����
            List<MonthlyClosingEmployInfo> list = new List<MonthlyClosingEmployInfo>();

            string errMsg = "";

            /*
             * ���ߏ��List.
            */
            Dictionary<int, ClosureInfo> closureList = ClosureLinker.GetClosureInfo(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                return list;
            }

            /*
             * �ٗp�^���ߏ��List.
            */
            Dictionary<string, EmploymentClosureInfo> employClosureList = ClosureLinker.GetEmploymentClosureInfo(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                return list;
            }

            /*
             * �����N���^����ID�^���ߊ��ԏ��List.
            */
            Dictionary<int, Dictionary<int, ClosurePeriodInfo>> monthlyClosurePeriodList = new Dictionary<int, Dictionary<int, ClosurePeriodInfo>>();

            /*
             * �x�����敪�ɊY������ٗp�^�Ώی�(�A�����̐ݒ�)���List.
            */
             List<PayEmployTargetMonthInfo> payEmployList = new List<PayEmployTargetMonthInfo>();

            foreach (PaymentInfo paymentInfo in TI.Setting.SmileOutputSetting.PaymentInfoList)
            {
                if (paymentInfo.Payment == TI.Output.Payment)
                {
                    payEmployList = paymentInfo.PayEmployTargetMonthInfoList;
                }
            }
            if (payEmployList.Count == 0)
            {
                TI.ErrorMsgList.Add("�x�����敪�ɊY������ٗp�^�Ώی���񂪐ݒ肳��Ă��܂���B�x�����敪:" + TI.Output.Payment.ToString());
                return list;
            }

            foreach (PayEmployTargetMonthInfo payEmployInfo in payEmployList)
            {
                MonthlyClosingEmployInfo info = new MonthlyClosingEmployInfo();

                #region �ٗp�^���߁^�����N���^���ߊ��ԏ��
                // �ٗpCD
                info.EmploymentCd = payEmployInfo.EmploymentCd;

                // ����ID
                if (employClosureList.ContainsKey(info.EmploymentCd))
                {
                    info.ClosureId = employClosureList[payEmployInfo.EmploymentCd].ClosureId;
                }
                else
                {
                    TI.ErrorMsgList.Add("�ٗp�ɊY���������ID���ݒ肳��Ă��܂���B�ٗpCD:" + info.EmploymentCd);
                    return list;
                }

                if (TI.Setting.SmileOutputSetting.CheckLockStatus)
                {
                    #region ���ʎ��т̃��b�N��Ԃ̃`�F�b�N
                    bool monthlyLock = MonthlyCheckLinker.LockStatusCheck(TI, info.ClosureId, ref errMsg);
                    if (errMsg != "")
                    {
                        TI.ErrorMsgList.Add("���ʎ��т̃��b�N��Ԃ̃`�F�b�N�����Ɏ��s���܂����B" + Environment.NewLine + errMsg);
                        return list;
                    }
                    if (!monthlyLock)
                    {
                        TI.ErrorMsgList.Add("���ʎ��яC���̃��b�N������Ă��܂���B�Ύ��Y�ɂĂ��m�F���������B" + Environment.NewLine + "����ID�F" + info.ClosureId.ToString());
                        return list;
                    }
                    #endregion
                }

                // �����N��(���ߌ��̓��� or �O��)
                int processMonth = closureList[info.ClosureId].CurrentMonth;
                if (payEmployInfo.TargetMonth == TargetMonth.Previous) // �O��
                {
                    processMonth = int.Parse(Toolbox.yyyyMMtoDateTime(processMonth).AddMonths(-1).ToString("yyyyMM"));
                }
                info.ProcessMonth = processMonth;

                // ���ߊ���(���ߊJ�n���`���ߏI����)

                // ����ID�^���ߊ��ԏ��List.
                Dictionary<int, ClosurePeriodInfo> closurePeriodList = new Dictionary<int, ClosurePeriodInfo>();
                if (monthlyClosurePeriodList.ContainsKey(processMonth))
                {
                    closurePeriodList = monthlyClosurePeriodList[processMonth];
                }
                else
                {
                    closurePeriodList = ClosureLinker.GetClosurePeriod(TI, processMonth, ref errMsg);
                    monthlyClosurePeriodList.Add(processMonth, closurePeriodList);
                }

                if (closurePeriodList.ContainsKey(info.ClosureId))
                {
                    info.StartDate = closurePeriodList[info.ClosureId].StartDate;
                    info.EndDate   = closurePeriodList[info.ClosureId].EndDate;
                }
                else
                {
                    TI.ErrorMsgList.Add("����ID�ɊY��������ߊ��ԏ����擾�ł��܂���B����ID:" + info.ClosureId.ToString());
                    return list;
                }
                #endregion �ٗp�^���߁^�����N���^���ߊ��ԏ��

                list.Add(info);
            }

            return list;
            #endregion
        }

        /// <summary>
        /// �ٗp�^���߁^�����N���^���ߊ��ԏ��List.���O���o�͂̏o�͊��Ԃ����߂�
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="startDate">�O���o�͊���-�J�n��</param>
        /// <param name="endDate">�O���o�͊���-�I����</param>
        public void CalculateOutputPeriod(TaskInfo TI, ref DateTime startDate, ref DateTime endDate)
        {
            #region �ٗp�^���߁^�����N���^���ߊ��ԏ��List.���O���o�͂̏o�͊��Ԃ����߂�
            // �����N���̏���List.
            SortedList<int, int> processMonthList = new SortedList<int, int>();

            foreach (MonthlyClosingEmployInfo monthlyClosingEmployInfo in TI.Output.MonthlyClosingEmployInfoList)
            {
                // �����N����ǉ�
                processMonthList.Add(monthlyClosingEmployInfo.ProcessMonth, monthlyClosingEmployInfo.ProcessMonth);
            }

            /*
             * Full����(�擪�`����)�ŊO���o�͂��o�͂��A���ʂɑ΂��Ďx�����敪�ɊY������ٗp���(�Ώ۔N��)�Ɋ�Â��A�ΏێЈ����i�荞��
            */

            // �擪���J�n��
            startDate = Toolbox.yyyyMMtoDateTime(processMonthList.Keys[0]);
            // �������I����
            endDate = Toolbox.yyyyMMtoDateTime(processMonthList.Keys[processMonthList.Count - 1]);
            #endregion
        }
    }
}
