using System;
using System.Collections.Generic;

namespace WSISmile.Business.Enum
{
    public class ProcessTypeEnumConverter
    {
        /// <summary>
        /// ���s���������ꗗ
        /// </summary>
        private static Dictionary<ProcessType, string> processWording = new Dictionary<ProcessType, string>();

        /// <summary>
        /// ���s�����������̂��擾����
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string GetProcessWording(ProcessType value)
        {
            if (processWording.Count == 0)
            {
                processWording.Add(ProcessType.Prepare, "�J�n-�O����");
                processWording.Add(ProcessType.WorkplaceInfo, "�g�D���A�g");
                processWording.Add(ProcessType.EmployeeBase, "�l����{���A�g");
                processWording.Add(ProcessType.EmployeeInfo, "�E�����A�g");
                processWording.Add(ProcessType.EmployeeAddress, "�Z�����A�g");
                processWording.Add(ProcessType.EmployeeSuspension, "�x�E���A�g");
                processWording.Add(ProcessType.EmployeeBelongs, "�����}�X�^�[�A�g");
                processWording.Add(ProcessType.EmployeeData, "�Ј��}�X�^�[�A�g");
                processWording.Add(ProcessType.MonthlyDataLink, "�ϓ����ڎ�荞��");
            }

            return processWording[value];
        }
    }
}
