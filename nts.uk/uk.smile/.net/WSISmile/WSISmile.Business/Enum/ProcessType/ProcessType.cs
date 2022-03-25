using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// 実行処理種類Enum
    /// </summary>
    public enum ProcessType
    {
        /// <summary>
        /// 開始処理
        /// </summary>
        Prepare = 0,
        /// <summary>
        /// 1: 組織情報連携
        /// </summary>
        WorkplaceInfo = 1,
        /// <summary>
        /// 2: 人事基本情報連携
        /// </summary>
        EmployeeBase = 2,
        /// <summary>
        /// 3: 職制情報連携
        /// </summary>
        EmployeeInfo = 3,
        /// <summary>
        /// 4: 住所情報連携
        /// </summary>
        EmployeeAddress = 4,
        /// <summary>
        /// 5: 休職情報連携
        /// </summary>
        EmployeeSuspension = 5,
        /// <summary>
        /// 6: 所属マスター連携
        /// </summary>
        EmployeeBelongs = 6,
        /// <summary>
        /// 7: 社員マスター連携
        /// </summary>
        EmployeeData = 7,
        /// <summary>
        /// 変動項目取り込み
        /// </summary>
        MonthlyDataLink = 8
    }
}
