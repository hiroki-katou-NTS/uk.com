using System;
using System.Collections.Generic;

namespace WSISmile.Business.Enum
{
    public class ProcessTypeEnumConverter
    {
        /// <summary>
        /// 実行処理文言一覧
        /// </summary>
        private static Dictionary<ProcessType, string> processWording = new Dictionary<ProcessType, string>();

        /// <summary>
        /// 実行処理文言名称を取得する
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string GetProcessWording(ProcessType value)
        {
            if (processWording.Count == 0)
            {
                processWording.Add(ProcessType.Prepare, "開始-前準備");
                processWording.Add(ProcessType.WorkplaceInfo, "組織情報連携");
                processWording.Add(ProcessType.EmployeeBase, "人事基本情報連携");
                processWording.Add(ProcessType.EmployeeInfo, "職制情報連携");
                processWording.Add(ProcessType.EmployeeAddress, "住所情報連携");
                processWording.Add(ProcessType.EmployeeSuspension, "休職情報連携");
                processWording.Add(ProcessType.EmployeeBelongs, "所属マスター連携");
                processWording.Add(ProcessType.EmployeeData, "社員マスター連携");
                processWording.Add(ProcessType.MonthlyDataLink, "変動項目取り込み");
            }

            return processWording[value];
        }
    }
}
