using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// 受入カテゴリEnum
    /// </summary>
    public enum AcceptCategory
    {
        /// <summary>
        /// 初期値
        /// </summary>
        None = 0,
        /// <summary>
        /// 1: 組織情報
        /// </summary>
        WorkplaceInfo = 1,
        /// <summary>
        /// 2: 人事基本情報
        /// </summary>
        EmployeeBase = 2,
        /// <summary>
        /// 3: 職制情報
        /// </summary>
        EmployeeInfo = 3,
        /// <summary>
        /// 4: 住所情報
        /// </summary>
        EmployeeAddress = 4,
        /// <summary>
        /// 5: 休職情報
        /// </summary>
        EmployeeSuspension = 5,
        /// <summary>
        /// 6: 所属マスター
        /// </summary>
        EmployeeBelongs = 6,
        /// <summary>
        /// 7: 社員マスター
        /// </summary>
        EmployeeData = 7
    }
}
