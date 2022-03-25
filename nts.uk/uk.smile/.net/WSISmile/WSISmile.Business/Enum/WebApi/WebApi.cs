using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// Webサービス
    /// </summary>
    public enum WebApi
    {
        /// <summary>
        /// ログイン
        /// </summary>
        Login,
        /// <summary>
        /// 契約情報チェック
        /// </summary>
        ContractCheck,
        /// <summary>
        /// 外部出力起動
        /// </summary>
        OutputStarting,
        /// <summary>
        /// 外部出力設定情報を取得
        /// </summary>
        GetOutputSetting,
        /// <summary>
        /// 外部出力処理状態チェック
        /// </summary>
        OutputStatusCheck,
        /// <summary>
        /// 外部出力一時ファイルのFileIDを取得
        /// </summary>
        GetOutputTempFileId,
        /// <summary>
        /// 外部受入->準備
        /// </summary>
        AcceptPrepare,
        /// <summary>
        /// 外部受入起動
        /// </summary>
        AcceptStarting,
        /// <summary>
        /// 外部受入処理状態チェック
        /// </summary>
        AcceptStatusCheck,
        /// <summary>
        /// 外部受入エラー情報チェック
        /// </summary>
        AcceptErrorInfoCheck,
        /// <summary>
        /// ファイルダウンロード処理
        /// </summary>
        FileDownload,
        /// <summary>
        /// ファイルアップロード処理
        /// </summary>
        FileUpload,
        /// <summary>
        /// 締め情報を取得
        /// </summary>
        GetClosureInfo,
        /// <summary>
        /// 雇用／締め情報を取得
        /// </summary>
        GetEmploymentClosureInfo,
        /// <summary>
        /// 締め期間(指定年月)*締めID単位を取得
        /// </summary>
        GetClosurePeriod,
        /// <summary>
        /// 月別実績の締め情報を取得
        /// </summary>
        GetMonthlyClosureInfo,
        /// <summary>
        /// 月別実績のロック状態のチェック
        /// </summary>
        MonthlyLockStatusCheck,
        /// <summary>
        /// 月別実績の承認状態のチェック
        /// </summary>
        MonthlyApproveStatusCheck,
        /// <summary>
        /// Smile連携-外部出力設定情報を取得
        /// </summary>
        GetSmileOutputSetting,
        /// <summary>
        /// Smile連携-外部受入設定情報を取得
        /// </summary>
        GetSmileAcceptSetting,
        /// <summary>
        /// 指定雇用(締め期間)による社員抽出
        /// </summary>
        SelectEmployeesByEmployment
    }
}
