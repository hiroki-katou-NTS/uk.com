#region using
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Services;

using WSISmile.Business.Category.Accept;
using WSISmile.Business.Category.Output;
using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Linker;
using WSISmile.Business.Link.Parameter.Accept;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Link.Parameter.Setting;
using WSISmile.Business.Log;
using WSISmile.Business.Task;
#endregion using

namespace KinErp.WSISmile
{
    /// <summary>
    /// Smile連携サービス
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    public class KinjirouDataConvert : System.Web.Services.WebService
    {
        #region コンストラクタ
        /// <summary>
        /// コンストラクタ
        /// </summary>
        public KinjirouDataConvert() { }
        #endregion

        #region Webメソッド

        #region 共通処理
        #region 初期化処理 : StartUp
        /// <summary>
        /// 初期化処理 : StartUp
        /// </summary>
        /// <param name="strId">Smile側の実行ユーザーID</param>
        /// <param name="intKaishaCd">会社コード</param>
        /// <param name="datBaseDate">基準日</param>
        /// <param name="intRenkeiKbn">0:外部受入／1:外部出力</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "Webサービスの利用準備")]
        public bool StartUp(string strId, int intKaishaCd, DateTime datBaseDate, int intRenkeiKbn)
        {
            bool blnRet = true;

            string errMsg = "";

            #region 契約コード＆パスワード取得チェック
            if (GetContractCode() == string.Empty || GetContractPassword() == string.Empty)
            {
                StartingErrorInfo SEI = StartingErrorInfo.Create(GetContractCode(), GetServerPath());
                if (SEI != null)
                {
                    SEI.ErrorMsg = "契約CDまたはパスワードを取得できません。連携URLの指定に誤りないかを確認してください。";
                    SEI.Save();
                }
                return false;
            }
            #endregion

            #region *** 排他制御 ***
            bool isExecuting = TaskInfo.IsExecuting(GetContractCode(), GetServerPath(), ref errMsg);
            if (errMsg != "")
            {
                StartingErrorInfo SEI = StartingErrorInfo.Create(GetContractCode(), GetServerPath());
                if (SEI != null)
                {
                    SEI.ErrorMsg = "Webサービスの排他制御開始に失敗しました。" + Environment.NewLine + errMsg;
                    SEI.Save();
                }
                return false;
            }
            if (isExecuting)
            {
                StartingErrorInfo SEI = StartingErrorInfo.Create(GetContractCode(), GetServerPath());
                if (SEI != null)
                {
                    SEI.ErrorMsg = "他のタスクが実行中です。しばらくお待ちして再実行してください。";
                    SEI.Save();
                }
                return false;
            }
            #endregion *** 排他制御 ***

            // タスク情報生成
            TaskInfo TI = TaskInfo.Create(GetContractCode(), GetContractPassword(), GetServerPath());
            if (TI == null)
            {
                return false;
            }
            TI.ErrorMsgList = new List<string>();

            // 実行処理種類
            TI.ProcessType = ProcessType.Prepare;

            // 会社コード＆会社ID
            TI.CompCode = intKaishaCd.ToString().PadLeft(4, '0');
            TI.CompCid = TI.Contract.Code + "-" + TI.CompCode;
            // ユーザーIDの保持(J-SOXログ用)
            TI.UserId = strId;

            #region ログイン認証処理＆クッキー情報発行
            // **ログイン処理**
            Authenticator.Login(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                TI.Save();
                return false;
            }

            // Save the Cookie.
            TI.Save();
            #endregion ログイン認証処理＆クッキー情報発行

            #region Smile連携設定情報の取得
            if (intRenkeiKbn == 0)
            {
                #region Smile連携-外部受入設定情報の取得
                TI.Setting.SmileAcceptSetting = SettingLinker.GetSmileAcceptSetting(TI, ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("Smile連携-外部受入設定情報の取得に失敗しました。" + Environment.NewLine + errMsg);
                    TI.Save();
                    return false;
                }

                if (TI.Setting.SmileAcceptSetting.CategoriesSetting.Count == 0)
                {
                    TI.ErrorMsgList.Add("Smile連携-外部受入設定情報を取得できませんでした。勤次郎－Smile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                #endregion
            }
            else
            {
                #region Smile連携-外部出力設定情報の取得
                TI.Setting.SmileOutputSetting = SettingLinker.GetSmileOutputSetting(TI, ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("Smile連携-外部出力設定情報の取得に失敗しました。" + Environment.NewLine + errMsg);
                    TI.Save();
                    return false;
                }

                if (TI.Setting.SmileOutputSetting.ConditionSetCd == string.Empty || TI.Setting.SmileOutputSetting.PaymentInfoList.Count == 0)
                {
                    TI.ErrorMsgList.Add("Smile連携-外部出力設定情報を取得できませんでした。勤次郎－Smile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                #endregion
            }
            #endregion

            #region 保管期間期限切れの情報ファイルを削除
            Logger.RemoveExpiredTaskFiles(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add("期限切れ情報ファイルの削除処理に失敗しました。" + Environment.NewLine + errMsg);
                TI.Save();
                return false;
            }
            #endregion

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 初期化処理 : StartUp

        #region 終了メソッド : End
        /// <summary>
        /// Webサービスの終了
        /// </summary>
        /// <returns></returns>
        [WebMethod(Description = "Webサービスを終了する。")]
        public bool End(int intRenkeiKbn)
        {
            string errMsg = "";

            // タスク実行前に、エラーが発生した場合
            StartingErrorInfo SEI = StartingErrorInfo.Load(GetContractCode(), GetServerPath());
            if (SEI != null)
            {
                // エラー情報クリア
                SEI.Clear(ref errMsg);
                if (errMsg != "")
                {
                    // TODO
                    string msg = "実行前エラー情報のクリア処理に失敗しました。" + Environment.NewLine + errMsg;
                    return false;
                }
                return true;
            }

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            if (TI != null)
            {
                TI.ErrorMsgList = new List<string>();

                // タスク情報クリア
                TI.Clear(ref errMsg);
                if (errMsg != "")
                {
                    string msg = "Webサービスの終了処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // プロセス間隔　*** 初期値:1.5秒 ***
                System.Threading.Thread.Sleep(int.Parse(ConfigurationManager.AppSettings["ProcessInterval"]));
            }

            return true;
        }
        #endregion 終了メソッド : End

        #region エラーメッセージの取得 : GetErrMsg
        /// <summary>
        /// エラーメッセージの取得 : GetErrMsg
        /// </summary>
        /// <returns>直前に実行したWebメソッド中で発生したメッセージリスト</returns>
        [WebMethod(Description = "エラーメッセージの取得")]
        public List<string> GetErrMsg()
        {
            List<string> errorMsgList = new List<string>();

            // タスク実行前に、エラーが発生した場合
            StartingErrorInfo SEI = StartingErrorInfo.Load(GetContractCode(), GetServerPath());
            if (SEI != null)
            {
                errorMsgList.Add(SEI.ErrorMsg);
                return errorMsgList;
            }

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            if (TI != null)
            {
                return TI.ErrorMsgList;
            }
            else
            {
                string accessError = "タスク情報の読み取り処理に失敗しました。" + Environment.NewLine;
                accessError += "連携サーバのTaskフォルダに適切なアクセス権限が付与されたことを確認してください。";

                errorMsgList.Add(accessError);
                return errorMsgList;
            }
        }
        #endregion メッセージの取得 : GetErrMsg
        #endregion 共通処理

        #region 個人情報連携 : SetParsonalInfo
        [WebMethod(Description = "Smile側で設定した人事情報を勤次郎の個人情報として保持する。")]
        public bool SetParsonalInfo(DataSet ds, int intMaxCnt)
        {
            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            return SetParsonalInfo(ds, intMaxCnt, ProcessType.EmployeeBase, TI);
        }

        /// <summary>
        /// 個人情報連携データの受入
        /// </summary>
        /// <param name="ds">個人情報データセット</param>
        /// <param name="intMaxCnt">最大処理件数</param>
        /// <param name="processType">対象区分:人事基本情報／社員マスター</param>
        /// <param name="TI">タスク情報</param>
        /// <remarks>Smile側で設定した人事情報を勤次郎の個人情報として保持する</remarks>
        /// <returns>True:正常終了 | False:異常終了</returns>
        private bool SetParsonalInfo(DataSet ds, int intMaxCnt, ProcessType processType, TaskInfo TI)
        {
            bool blnRet = true;

            string errMsg = "";

            EmployeeBase EmployeeBase = new EmployeeBase();

            // 実行処理種類
            TI.ProcessType = processType;

            try
            {
                #region 各種チェック
                if (ds == null || ds.Tables.Count <= 0 || ds.Tables[0].Rows.Count <= 0)
                {
                    // 情報が無ければ[正常終了]とする
                    return true;
                }

                if (processType == ProcessType.EmployeeBase)
                {
                    // 人事基本情報
                    TI.Accept.ConditionSetCd = TI.Setting.SmileAcceptSetting.GetConditionSetCdByCategory(AcceptCategory.EmployeeBase);
                    if (TI.Accept.ConditionSetCd == "")
                    {
                        TI.ErrorMsgList.Add(TI.GetProcessWording() + "：外部受入条件設定コードを取得できません。勤次郎－Smile連携設定画面をご確認ください。");
                        TI.Save();
                        return false;
                    }
                }
                else if (processType == ProcessType.EmployeeData)
                {
                    // 社員マスター
                    TI.Accept.ConditionSetCd = TI.Setting.SmileAcceptSetting.GetConditionSetCdByCategory(AcceptCategory.EmployeeData);
                    if (TI.Accept.ConditionSetCd == "")
                    {
                        TI.ErrorMsgList.Add(TI.GetProcessWording() + "：外部受入条件設定コードを取得できません。勤次郎－Smile連携設定画面をご確認ください。");
                        TI.Save();
                        return false;
                    }
                }
                else
                {
                    throw new Exception("指定した実行処理種類をサポートしていません。種類No.:" + processType.ToString());
                }
                #endregion 各種チェック

                // 起動ログ TODO

                #region Smile側からもらった連携データで受入ファイル生成
                // *** 必要な項目編集 ***
                EmployeeBase.SmileDataEditing(ds.Tables[0]);

                // Smile側からもらった連携データを契約単位フォルダにに保存(CSVファイルに書き込み)
                EmployeeBase.AcceptFileCreate(TI, ds.Tables[0], TI.GetProcessWording(), TI.Accept.RowCounter != 0, ref errMsg);
                if (TI.Accept.File == "" || errMsg != "")
                {
                    string msg = "連携ファイルのサーバー保存処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                TI.Accept.RowCounter = ds.Tables[0].Rows.Count;
                // 総件数
                TI.Accept.TotalCount += TI.Accept.RowCounter;

                // 総件数チェック： 全てのデータ(intMaxCnt件)を受け取るまで、ここから先の処理にはいかない。
                if (TI.Accept.RowCounter < intMaxCnt)
                {
                    // 次のデータ受取へ
                    TI.Save();
                    return true;
                }
                #endregion Smile側からもらった連携データで受入ファイル生成
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "人事基本情報の連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }

        #endregion 個人情報設定 : SetParsonalInfo

        #region 職場情報連携 : SetOrganizeInfo
        /// <summary>
        /// 職場情報連携データの受入 : SetOrganizeInfo
        /// </summary>
        /// <param name="pData">職場情報データセット</param>
        /// <param name="intMaxCnt">今回連携する最大件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        /// <remarks>Smile側で設定した組織情報を勤次郎の職場情報として保持する。
        /// 戻り値をBool型に変更・メッセージを内部変数で持つように変更
        /// </remarks>
        [WebMethod(Description = "Smile側で設定した組織情報を勤次郎の職場情報として保持する。")]
        public bool SetOrganizeInfo(DataSet ds, ref int intCnt)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            WorkplaceInfo WorkplaceInfo = new WorkplaceInfo();

            // 実行処理種類
            TI.ProcessType = ProcessType.WorkplaceInfo;

            try
            {
                #region 各種チェック
                if (ds == null || ds.Tables[0].Rows.Count <= 0)
                {
                    // 情報が無ければ処理しない
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "の連携データが存在しませんでした。");
                    TI.Save();
                    return false;
                }

                TI.Accept.ConditionSetCd = TI.Setting.SmileAcceptSetting.GetConditionSetCdByCategory(AcceptCategory.WorkplaceInfo);
                if (TI.Accept.ConditionSetCd == "")
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "：外部受入条件設定コードを取得できません。勤次郎－Smile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                #endregion 各種チェック

                // 起動ログ TODO

                #region Smile側からもらった連携データで受入ファイル生成
                // Smile組織情報を 階層再構築 ＆ 編集 ＆ List.へ変換
                List<SmileOrganization> listSmileOrganization = WorkplaceInfo.SmileDataToList(ds, TI);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }

                // 職場情報の内部階層CD[1～10]を設定する
                WorkplaceInfo.SetWorkplaceInLevelCd(listSmileOrganization, TI);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }

                // Smile組織情報List.を連携DataTableへ変換
                DataTable dtSmile = WorkplaceInfo.ListToSmileLinkTable(listSmileOrganization);

                // Smile側からもらった連携データを契約単位フォルダにに保存(CSVファイルに書き込み)
                WorkplaceInfo.AcceptFileCreate(TI, dtSmile, TI.GetProcessWording(), false, ref errMsg);
                if (TI.Accept.File == "" || errMsg != "")
                {
                    string msg = "連携ファイルのサーバー保存処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 総件数
                TI.Accept.TotalCount = dtSmile.Rows.Count;
                #endregion Smile側からもらった連携データで受入ファイル生成
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "職場情報の連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // エラーメッセージの最大表示件数整合
            TI.ErrorMsgList = Logger.ErrorMsgLimit(TI.ErrorMsgList);
            if (TI.ErrorMsgList.Count > 0)
            {
                blnRet = false;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 職場情報連携データの受入 : SetOrganizeInfo

        #region 住所情報連携 : SetAddressInfo
        /// <summary>
        /// 住所情報連携データの受入
        /// </summary>
        /// <param name="ds">住所情報</param>
        /// <param name="intCnt">受入成功データ件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        [WebMethod(Description = "Smile側で設定した住所情報を勤次郎の現住所情報として登録する。")]
        public bool SetAddressInfo(DataSet ds, ref int intCnt)
        {
            bool blnRet = true;

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            TI.ErrorMsgList.Add("*** 住所情報連携がサポートされていません。 ***");
            if (TI.ErrorMsgList.Count > 0)
            {
                return false;
            }

            // 実行処理種類
            TI.ProcessType = ProcessType.EmployeeAddress;

            try
            {
                intCnt = 0;
                
                #region 各種チェック TODO
                /*
                if (ds == null || ds.Tables[0].Rows.Count <= 0)
                {
                    // 情報が無ければ処理しない
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "の連携データが存在しませんでした。");
                    TI.Save();
                    return false;
                }

                // 受入連動区分の確認
                if (SI.mSettingManager.受入連動区分4 != "1")
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "は連携しない設定になっています。勤次郎－外部受入のSmile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }

                // 住所情報条件設定コードの保持
                SI.mAddress.ConditionCode = SI.mSettingManager.受入条件NO4;
                if (SI.mAddress.ConditionCode == "")
                {
                    // 条件NOが入っていない場合は処理しない
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "：条件設定コードが存在しません。勤次郎－外部受入のSmile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                */
                #endregion 各種チェック TODO

                // 起動ログ TODO

                #region 受入前準備 TODO
                /*
                // 受入元ファイルのファイルパスを取得する
                SI.mAddress.GetAcceptFilePath(TI.ErrorMsgList);

                // 情報を社員CD,staratdate,enddateでソートする
                ds.Tables[0].DefaultView.Sort = "001, 002, 004";
                DataSet dsAddress = new DataSet();
                dsAddress.Tables.Add(ds.Tables[0].DefaultView.ToTable());

                // 連携情報の設定
                SI.mAddress.DataInfo = dsAddress;

                // CSVファイル出力
                SI.mAddress.OutputToCSVFile(TI.CompCid, (int)TI.ProcessType, TI);

                // 共通エラーメッセージ
                string strTopErrMsg = TI.GetProcessWording() + "：会社コード=" + TI.CompCid + " 社員コード=## ⇒ ";
                */
                #endregion 受入前準備 TODO

                #region 社員CD、職場CDコード編集 TODO
                /*
                // 社員CDの有効桁区分、有効桁開始、有効桁長、コード編集区分、コード編集の取得
                CodeEdit codeEdit_syacd = new CodeEdit(CodeEdit.MAXLENGTH_SYACD);
                AcceptManager.GetItemSetting_Keta(SI.mAddress.ConditionCode, 4m, codeEdit_syacd);
                AcceptManager.GetItemSetting_Padding(SI.mAddress.ConditionCode, 4m, codeEdit_syacd);
                */
                #endregion 社員CD、職場CDコード編集 TODO

                #region DB更新処理実行 TODO
                /*
                SortedList syain_info = new SortedList();
                foreach (DataRow dr in dsAddress.Tables[0].DefaultView.Table.Rows)
                {
                    #region Loop処理
                    bool db_open = false;
                    Address.dtoAddressInfo address = new Address.dtoAddressInfo();

                    try
                    {
                        #region 社員CD(固定項目)
                        if (dr[Address.SmAddressItemName.syacd] == null || dr[Address.SmAddressItemName.syacd] == DBNull.Value)
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", "なし") + "社員コードに設定が不正です。");
                            continue;
                        }
                        if (!RegularExpressionsUtil.IsCode(dr[Address.SmAddressItemName.syacd].ToString().Trim(), RegularExpressionsUtil.eCODE_TYPE.SYAIN_CODE))
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "社員コードに不正な文字が含まれています。");
                            continue;
                        }

                        // 社員CD編集
                        address.syacd = codeEdit_syacd.EditPad(codeEdit_syacd.EditKeta(dr[Address.SmAddressItemName.syacd].ToString()));

                        #region SIDを取得
                        if (!syain_info.ContainsKey(TI.CompCid + "/" + address.syacd))
                        {
                            string sid = PersonManager.GetSidFromSyainCode(TI.CompCid, address.syacd);
                            if (sid == null)
                            {
                                TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "個人情報取得に失敗しました。");
                                continue;
                            }
                            address.sid = sid;

                            syain_info.Add(TI.CompCid + "/" + address.syacd, address.sid);
                        }
                        else
                        {
                            address.sid = syain_info[TI.CompCid + "/" + address.syacd].ToString();
                        }
                        #endregion SIDを取得

                        #endregion 社員CD(固定項目)

                        #region 転居日(固定項目)
                        if (dr[Address.SmAddressItemName.tenkyo] == null || dr[Address.SmAddressItemName.tenkyo] == DBNull.Value)
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "転居日に設定が不正です。");
                            continue;
                        }
                        if (!RegularExpressionsUtil.IsDate(dr[Address.SmAddressItemName.tenkyo].ToString()))
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "転居日に日付ではない値が含まれています。値:" + dr[Address.SmAddressItemName.tenkyo].ToString());
                            continue;
                        }
                        address.tenkyo = DateTime.Parse(ConversionDate.strValidDate(dr[Address.SmAddressItemName.tenkyo].ToString(), 1));
                        #endregion 転居日(固定項目)

                        #region 転出日(固定項目)
                        if (dr[Address.SmAddressItemName.tensyutu] == null || dr[Address.SmAddressItemName.tensyutu] == DBNull.Value)
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "転出日に設定が不正です。");
                            continue;
                        }
                        if (dr[Address.SmAddressItemName.tensyutu].ToString() != "99999999")
                        {
                            if (!RegularExpressionsUtil.IsDate(dr[Address.SmAddressItemName.tensyutu].ToString()))
                            {
                                TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "転出日に日付ではない値が含まれています。値:" + dr[Address.SmAddressItemName.tensyutu].ToString());
                                continue;
                            }
                            address.tensyutu = DateTime.Parse(ConversionDate.strValidDate(dr[Address.SmAddressItemName.tensyutu].ToString(), 1));
                        }
                        else
                        {
                            address.tensyutu = new DateTime(9999, 12, 31);
                        }
                        #endregion 転出日(固定項目)

                        #region その他(固定項目)
                        // 郵便番号
                        if (dr[Address.SmAddressItemName.yubin_no] == null || dr[Address.SmAddressItemName.yubin_no] == DBNull.Value)
                            address.yubin_no = "";
                        else
                            address.yubin_no = Toolbox.RemoveInvalidSymbol(dr[Address.SmAddressItemName.yubin_no].ToString());
                        // 住所1
                        if (dr[Address.SmAddressItemName.jusyo1] == null || dr[Address.SmAddressItemName.jusyo1] == DBNull.Value)
                            address.jusyo1 = "";
                        else
                            address.jusyo1 = Toolbox.RemoveInvalidSymbol(dr[Address.SmAddressItemName.jusyo1].ToString());
                        // 住所2
                        if (dr[Address.SmAddressItemName.jusyo2] == null || dr[Address.SmAddressItemName.jusyo2] == DBNull.Value)
                            address.jusyo2 = "";
                        else
                            address.jusyo2 = Toolbox.RemoveInvalidSymbol(dr[Address.SmAddressItemName.jusyo2].ToString());
                        // 電話番号
                        if (dr[Address.SmAddressItemName.tel_no] == null || dr[Address.SmAddressItemName.tel_no] == DBNull.Value)
                            address.tel_no = "";
                        else
                            address.tel_no = dr[Address.SmAddressItemName.tel_no].ToString();
                        #endregion

                        db_open = true;
                        AddData.OpenMain();
                        AddData.BeginTransactionMain();

                        // 現在の現住所データを取得
                        DataTable aAddress = AddData.GetAddressdt(decimal.Parse(address.sid));
                        string strErrMes = "";
                        if (aAddress.Rows.Count > 0)
                        {
                            #region 現住所データの更新及び削除
                            foreach (DataRow drAdd in aAddress.Rows)
                            {
                                if (address.tenkyo > ((DateTime)(drAdd[TC.jm_genadd.startdate])))
                                {
                                    if (address.tenkyo <= ((DateTime)(drAdd[TC.jm_genadd.enddate])))
                                    {
                                        // 範囲を切る
                                        strErrMes = AddData.UpdateAddressdt
                                            (
                                                drAdd[TC.jm_genadd.kojin_id].ToString()
                                                , (DateTime)drAdd[TC.jm_genadd.startdate]
                                                , address.tenkyo.AddDays(-1)
                                            );

                                        if (strErrMes != "")
                                        {
                                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "更新失敗/" + strErrMes);
                                            throw new AddressUpdException();
                                        }
                                    }
                                }
                                else
                                {
                                    // 未来履歴データを消す
                                    strErrMes = AddData.DeleteAddressdt
                                        (
                                            drAdd[TC.jm_genadd.kojin_id].ToString()
                                            , (DateTime)drAdd[TC.jm_genadd.startdate]
                                        );

                                    if (strErrMes != "")
                                    {
                                        TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "削除失敗/" + strErrMes);
                                        throw new AddressUpdException();
                                    }

                                }
                            }
                            #endregion
                        }

                        // データをInsert
                        strErrMes = AddData.InsertAddressdt(address, TI.CompCid);
                        if (strErrMes != "")
                        {
                            TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "新規登録失敗/" + strErrMes);
                            throw new AddressUpdException();
                        }
                        else
                        {
                            intCnt++;
                            AddData.CommitTransaction();
                            AddData.CloseIfOpenedMain();
                        }
                    }
                    catch (AddressUpdException ex)
                    {
                        string strErr = ex.ToString();

                        if (db_open)
                        {
                            AddData.RollBack();
                            AddData.CloseIfOpenedMain();
                        }
                    }
                    catch(Exception e)
                    {
                        TI.ErrorMsgList.Add(strTopErrMsg.Replace("##", dr[Address.SmAddressItemName.syacd].ToString()) + "例外発生/" + e.Message);
                        if (db_open)
                        {
                            AddData.RollBack();
                            AddData.CloseIfOpenedMain();
                        }
                    }
                    AddData.CloseIfOpenedMain();
                    #endregion Loop処理
                }
                */
                #endregion DB更新処理実行 TODO
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "住所情報の連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // エラーメッセージの最大表示件数整合
            TI.ErrorMsgList = Logger.ErrorMsgLimit(TI.ErrorMsgList);
            if (TI.ErrorMsgList.Count > 0)
            {
                blnRet = false;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 住所情報連携 : SetAddressInfo

        #region 職制情報連携 : SetShokuseiInfo
        /// <summary>
        /// 職制情報連携データの受入
        /// </summary>
        /// <param name="ds">職制情報</param>
        /// <param name="intCnt">受入成功データ件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        [WebMethod(Description = "Smile側で設定した職制情報を勤次郎の職位情報として登録する。")]
        public bool SetShokuseiInfo(DataSet ds, ref int intCnt)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            EmployeeInfo EmployeeInfo = new EmployeeInfo();

            // 実行処理種類
            TI.ProcessType = ProcessType.EmployeeInfo;

            try
            {                
                intCnt = 0;

                #region 各種チェック
                if (ds == null || ds.Tables[0].Rows.Count <= 0)
                {
                    // 情報が無ければ処理しない
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "の連携データが存在しませんでした。");
                    TI.Save();
                    return false;
                }

                TI.Accept.ConditionSetCd = TI.Setting.SmileAcceptSetting.GetConditionSetCdByCategory(AcceptCategory.EmployeeInfo);
                if (TI.Accept.ConditionSetCd == "")
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "：外部受入条件設定コードを取得できません。勤次郎－Smile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                #endregion 各種チェック

                // 起動ログ TODO

                // 社員コード＆発令年月日でソート
                ds.Tables[0].DefaultView.Sort = EmployeeInfoItem.EMPLOYEE_CD + ", " + EmployeeInfoItem.ANNOUNCE_DAY;

                #region Smile側からもらった連携データで受入ファイル生成
                // *** 必要な項目編集 ***
                EmployeeInfo.SmileDataEditing(ds.Tables[0]);

                // Smile側からもらった連携データを契約単位フォルダにに保存(CSVファイルに書き込み)
                EmployeeInfo.AcceptFileCreate(TI, ds.Tables[0], TI.GetProcessWording(), false, ref errMsg);
                if (TI.Accept.File == "" || errMsg != "")
                {
                    string msg = "連携ファイルのサーバー保存処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 総件数
                TI.Accept.TotalCount = ds.Tables[0].Rows.Count;

                // タスク情報保存
                TI.Save();
                #endregion Smile側からもらった連携データで受入ファイル生成

                // *** 外部受入実行 ***
                this.AcceptBegin();

                // *** 外部受入成功件数 ***
                this.GetAcceptCount(ref intCnt);
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "職制情報の連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // エラーメッセージの最大表示件数整合
            TI.ErrorMsgList = Logger.ErrorMsgLimit(TI.ErrorMsgList);
            if (TI.ErrorMsgList.Count > 0)
            {
                blnRet = false;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 職制情報連携 : SetShokuseiInfo

        #region 休職情報連携 : SetKyushokuInfo
        /// <summary>
        /// 休職情報連携データの受入
        /// </summary>
        /// <param name="ds">休職情報</param>
        /// <param name="intCnt">受入成功データ件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        [WebMethod(Description = "Smile側で設定した休職情報を勤次郎の休職休業情報として登録する。")]
        public bool SetKyushokuInfo(DataSet ds, ref int intCnt)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            EmployeeSuspension EmployeeSuspension = new EmployeeSuspension();

            // 実行処理種類
            TI.ProcessType = ProcessType.EmployeeSuspension;

            try
            {
                intCnt = 0;

                #region 各種チェック
                if (ds == null || ds.Tables[0].Rows.Count <= 0)
                {
                    // 情報が無ければ処理しない
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "の連携データが存在しませんでした。");
                    TI.Save();
                    return false;
                }

                TI.Accept.ConditionSetCd = TI.Setting.SmileAcceptSetting.GetConditionSetCdByCategory(AcceptCategory.EmployeeSuspension);
                if (TI.Accept.ConditionSetCd == "")
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "：外部受入条件設定コードを取得できません。勤次郎－Smile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                #endregion 各種チェック

                // 起動ログ TODO

                // 社員コード＆休職開始日でソート
                ds.Tables[0].DefaultView.Sort = EmployeeSuspensionItem.EMPLOYEE_CD + ", " + EmployeeSuspensionItem.START_DAY;

                #region Smile側からもらった連携データで受入ファイル生成
                // *** 必要な項目編集 ***
                EmployeeSuspension.SmileDataEditing(ds.Tables[0]);

                // Smile側からもらった連携データを契約単位フォルダにに保存(CSVファイルに書き込み)
                EmployeeSuspension.AcceptFileCreate(TI, ds.Tables[0], TI.GetProcessWording(), false, ref errMsg);
                if (TI.Accept.File == "" || errMsg != "")
                {
                    string msg = "連携ファイルのサーバー保存処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 総件数
                TI.Accept.TotalCount = ds.Tables[0].Rows.Count;

                // タスク情報保存
                TI.Save();
                #endregion Smile側からもらった連携データで受入ファイル生成

                // *** 外部受入実行 ***
                this.AcceptBegin();

                // *** 外部受入成功件数 ***
                this.GetAcceptCount(ref intCnt);
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "休職情報の連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // エラーメッセージの最大表示件数整合
            TI.ErrorMsgList = Logger.ErrorMsgLimit(TI.ErrorMsgList);
            if (TI.ErrorMsgList.Count > 0)
            {
                blnRet = false;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 休職情報連携 : SetKyushokuInfo

        #region 社員マスター連携 : SetEmployeeMst
        /// <summary>
        /// 社員マスターデータの受入
        /// </summary>
        /// <param name="ds">社員マスター情報</param>
        /// <param name="intMaxCnt">最大処理件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        [WebMethod(Description = "Smileの社員マスターを勤次郎の個人情報として保持する。")]
        public bool SetEmployeeMst(DataSet ds, int intMaxCnt)
        {
            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            TI.ErrorMsgList.Add("*** 社員マスター連携がサポートされていません。 ***");
            if (TI.ErrorMsgList.Count > 0)
            {
                return false;
            }

            return SetParsonalInfo(ds, intMaxCnt, ProcessType.EmployeeData, TI);
        }
        #endregion 社員マスター連携 : SetEmployeeMst

        #region 所属マスター連携 : SetAffiliationMst
        /// <summary>
        /// 所属マスターデータの受入
        /// </summary>
        /// <param name="ds">所属マスター情報</param>
        /// <param name="intMaxCnt">最大処理件数</param>
        /// <returns>True:正常終了 | False:異常終了</returns>
        [WebMethod(Description = "Smileの所属マスターを勤次郎の社員情報として保持する。")]
        public bool SetAffiliationMst(DataSet ds, int intMaxCnt)
        {
            bool blnRet = true;

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            TI.ErrorMsgList.Add("*** 所属マスター連携がサポートされていません。 ***");
            if (TI.ErrorMsgList.Count > 0)
            {
                return false;
            }

            // 実行処理種類
            TI.ProcessType = ProcessType.EmployeeBelongs;

            try
            {
                #region 各種チェック TODO
                /*
                if (ds == null || ds.Tables[0].Rows.Count <= 0)
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "の連携データが存在しませんでした。");
                    TI.Save();
                    return false;
                }
                else
                {
                    // 所属マスター連携は実行しない
                    if (SI.mSettingManager.受入連動区分7 != "1")
                    {
                        TI.ErrorMsgList.Add(TI.GetProcessWording() + "は連携しない設定になっています。勤次郎－外部受入のSmile連携設定画面をご確認ください。");
                        TI.Save();
                        return false;
                    }
                }

                // 所属情報条件設定コードの保持
                SI.mAffiliation.ConditionCode = SI.mSettingManager.受入条件NO7;
                if (SI.mAffiliation.ConditionCode == "")
                {
                    TI.ErrorMsgList.Add(TI.GetProcessWording() + "：条件設定コードが存在しません。勤次郎－外部受入のSmile連携設定画面をご確認ください。");
                    TI.Save();
                    return false;
                }
                */
                #endregion 各種チェック TODO

                // 起動ログ TODO

                #region レコード受取 TODO
                /*
                if (SI.mAffiliation.DataInfo == null)
                {
                    // 初回
                    SI.mAffiliation.DataInfo = ds;
                }
                else
                {
                    // レコード追加
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        SI.mAffiliation.DataInfo.Tables[0].ImportRow(ds.Tables[0].Rows[i]);
                    }
                }

                // 総件数チェック
                // 全てのデータ(intMaxCnt件)を受け取るまで、ここから先の処理にはいかない。
                if (SI.mAffiliation.DataInfo.Tables[0].Rows.Count < intMaxCnt)
                {
                    // 次のデータ受取へ
                    return true;
                }
                */
                #endregion レコード受取 TODO

                #region 受入前準備 TODO
                /*
                // 受入元ファイルのファイルパスを取得する
                SI.mAffiliation.GetAcceptFilePath(TI.ErrorMsgList);

                SI.mAffiliation.DataInfo.Tables[0].DefaultView.Sort = "001";
                DataTable dtAffiliation = SI.mAffiliation.DataInfo.Tables[0].DefaultView.ToTable();
                SI.mAffiliation.sAffInfo = new Affiliation.sAffiliationInfo[dtAffiliation.Rows.Count];
                */
                #endregion 受入前準備 TODO

                #region 必要な項目編集 TODO
                /*
                for (int i = 0; i < dtAffiliation.Rows.Count; i++)
                {
                    #region Loop処理
                    // 給与支給会社の組織情報
                    SI.mAffiliation.sAffInfo[i].strKaishaCd = TI.CompCid.ToString();

                    // 所属コード
                    if (!Toolbox.IsNull(dtAffiliation.Rows[i][0]))
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationCd = dtAffiliation.Rows[i][0].ToString();
                    }
                    else
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationCd = "";
                    }

                    // 所属名
                    if (!Toolbox.IsNull(dtAffiliation.Rows[i][1]))
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationName = Toolbox.RemoveInvalidSymbol(dtAffiliation.Rows[i][1].ToString());
                    }
                    else
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationName = "";
                    }

                    // 所属名略称
                    if (!Toolbox.IsNull(dtAffiliation.Rows[i][2]))
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationNamer = Toolbox.RemoveInvalidSymbol(dtAffiliation.Rows[i][2].ToString());
                    }
                    else
                    {
                        SI.mAffiliation.sAffInfo[i].strAffiliationNamer = "";
                    }

                    // 階層CD1
                    SI.mAffiliation.sAffInfo[i].strInlevelCd1 = (Organize.LEVEL_CD + i).ToString();
                    #endregion Loop処理
                }
                */
                #endregion 必要な項目編集 TODO

                #region 必須チェック TODO
                /*
                TI.ErrorMsgList = SI.mAffiliation.CheckRequiredValue(TI.CompCid);
                if (TI.ErrorMsgList.Count > 0)
                {
                    blnRet = false;
                }
                */
                #endregion 必須チェック TODO
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "所属マスターの連携処理中に例外が発生しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // エラーメッセージの最大表示件数整合
            TI.ErrorMsgList = Logger.ErrorMsgLimit(TI.ErrorMsgList);
            if (TI.ErrorMsgList.Count > 0)
            {
                blnRet = false;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 所属マスター連携 : SetAffiliationMst

        #region 連携処理(外部受入) : AcceptBegin
        /// <summary>
        /// 連携処理(外部受入)
        /// </summary>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "連携実行(外部受入)を実行する。")]
        public bool AcceptBegin()
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            try
            {
                // 外部受入Fileをアップロードする
                List<UploadFileInfo> uploadFiles = AcceptLinker.AcceptFileUpload(TI, TI.Accept.File, ref errMsg);
                if (uploadFiles.Count == 0 || errMsg != "")
                {
                    string msg = "受入ファイルのアップロード処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
                TI.Accept.FileId = uploadFiles[0].Id; // 1個のみ想定.

                blnRet = BeginInput(TI);
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "連携処理の実行中に例外が発生しました。" + Environment.NewLine + ex.Message; ;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }
            finally
            {
                // 連携情報の破棄?
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 連携処理(受入) : AcceptBegin

        #region 外部受入実行 : BeginInput
        /// <summary>
        /// 外部受入実行
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <returns>True：エラーなし / False：エラー発生</returns>
        private bool BeginInput(TaskInfo TI)
        {
            bool blnRet = true;

            string errMsg = "";

            try
            {
                // *** 外部受入前準備 ***
                AcceptTaskInfo prepareTask = AcceptLinker.AcceptPrepare(TI, ref errMsg);
                if (TI.Accept.PrepareTaskId == "" || errMsg != "")
                {
                    string msg = "外部受入前準備を開始できませんでした。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                if (prepareTask.Pending || prepareTask.Running)
                {
                    // *** 外部受入前準備のタスク完了を待つ ***
                    bool result = AcceptLinker.AwaitTaskCompleted(TI, TI.Accept.PrepareTaskId, ref errMsg);
                    if (result == false || errMsg != "")
                    {
                        string msg = "外部受入前準備のタスク実行に失敗しました。" + Environment.NewLine + errMsg;
                        TI.ErrorMsgList.Add(msg);
                        TI.Save();
                        Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                        return false;
                    }
                }

                // *** 外部受入前準備のエラーチェック ***
                List<string> errorsPrepare = AcceptLinker.HasProcessErrors(TI, ref errMsg);
                if (errorsPrepare.Count > 0)
                {
                    string msg = "外部受入前準備が完了しましたが、以下のエラーが発生しています。";
                    TI.ErrorMsgList.Add(msg);
                    TI.ErrorMsgList.AddRange(errorsPrepare);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // *** 外部受入実行 ***
                AcceptLinker.AcceptStarting(TI, ref errMsg);
                if (TI.Accept.ExecuteTaskId == "" || errMsg != "")
                {
                    string msg = "外部受入実行を開始できませんでした。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = TI.GetProcessWording() + "：外部受入の実行に失敗しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 外部受入実行 : BeginInput

        #region 連携処理(外部出力) : OutputBegin
        /// <summary>
        /// 連携処理(外部出力) (変動処理)
        /// </summary>
        /// <param name="intYear">処理年度</param>
        /// <param name="intMonth">支給月</param>
        /// <param name="strPaymentKbn">支給日区分</param>
        /// <param name="strAffKbn">所属区分(*未使用)</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "変動処理を実行する。")]
        public bool OutputBegin(int intYear, int intMonth, string strPaymentKbn, string strAffKbn)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            // 実行処理種類
            TI.ProcessType = ProcessType.MonthlyDataLink;

            try
            {
                // 起動ログ TODO

                // 外部出力期間(年月)、支払日区分
                TI.Output.SmileYear  = intYear;
                TI.Output.SmileMonth = intMonth;
                TI.Output.Payment = int.Parse(strPaymentKbn);

                #region 外部出力実行
                // 外部出力の設定情報を取得する
                TI.Output.Setting = OutputLinker.GetOutputSetting(TI, TI.Setting.SmileOutputSetting.ConditionSetCd, ref errMsg);
                if (errMsg != "")
                {
                    string msg = "外部出力の設定情報の取得に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
                if (TI.Output.Setting.ItemCodeList.Count == 0)
                {
                    string msg = "外部出力の設定情報を取得できませんでした。外部出力条件No:" + TI.Output.Setting.ConditionSetCd;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 雇用／締め・処理年月関連
                MonthlyClosing monthlyClosing = new MonthlyClosing();

                // 支払日区分に該当する雇用／締め／処理年月／締め期間情報リストを取得する
                TI.Output.MonthlyClosingEmployInfoList = monthlyClosing.LoadMonthlyClosingEmployInfo(TI);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }
                if (TI.Output.MonthlyClosingEmployInfoList.Count == 0)
                {
                    string msg = "支払日区分に該当する雇用／締め／処理年月／締め期間情報を取得できませんでした。";
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 外部出力実行
                blnRet = BeginOutput(TI);
                #endregion
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = TI.GetProcessWording() + "：外部出力の実行に失敗しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 連携処理(外部出力) : OutputBegin

        #region 外部出力実行 : BeginOutput
        /// <summary>
        /// 外部出力実行
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "外部出力実行(内部関数)")]
        private bool BeginOutput(TaskInfo TI)
        {
            bool blnRet = true;

            string errMsg = "";

            try
            {
                // 外部出力期間-開始日
                DateTime startDate = new DateTime(TI.Output.SmileYear, TI.Output.SmileMonth, 1);
                // 外部出力期間-終了日
                DateTime endDate = new DateTime(TI.Output.SmileYear, TI.Output.SmileMonth, 1);

                // 雇用／締め／処理年月／締め期間情報List.より外部出力の出力期間を求める
                (new MonthlyClosing()).CalculateOutputPeriod(TI, ref startDate, ref endDate);

                OutputLinker.OutputStarting(TI, startDate, endDate, ref errMsg);
                if (TI.Output.TaskId == "" || errMsg != "")
                {
                    string msg = "タスクIDを取得できませんでした。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = TI.GetProcessWording() + "：外部出力の実行に失敗しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 外部出力実行 : BeginOutput

        #region 受入処理のポーリング : AcceptPolling
        /// <summary>
        /// 受入処理のポーリング
        /// </summary>
        /// <param name="intStatus">0:完了／1:実行中</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "問い合わせに対し、連携処理(受入)の実行ステータスを返す。")]
        public bool AcceptPolling(ref int intStatus)
        {
            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            string errMsg = "";

            AcceptTaskInfo acceptTaskInfo = AcceptLinker.AcceptStatusCheck(TI, TI.Accept.ExecuteTaskId, ref errMsg);
            if (acceptTaskInfo == null || errMsg != "")
            {
                string msg = "外部受入の実行監視に失敗しました。" + Environment.NewLine + errMsg;
                TI.ErrorMsgList.Add(msg);
                TI.Save();
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                return false;
            }

            if (acceptTaskInfo.Pending == false || acceptTaskInfo.Running == false)
            {
                if (acceptTaskInfo.Succeeded == true)
                {
                    intStatus = 0;
                }
                else
                {
                    // 処理終了したが、成功ではない(内部エラーが発生した場合など)
                    string msg = "外部受入の実行が終了しましたが、不明なエラーが発生しました。" + Environment.NewLine + acceptTaskInfo.GetAcceptTaskError();
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
            }
            else
            {
                intStatus = 1;
            }

            // タスク情報保存
            TI.Save();

            return true;
        }
        #endregion 受入処理のポーリング : AcceptPolling

        #region 出力処理のポーリング : OutputPolling
        /// <summary>
        /// 出力処理のポーリング : OutputPolling
        /// </summary>
        /// <param name="intStatus">0:完了／1:実行中</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "問い合わせに対し、連携処理(出力)の実行ステータスを返す。")]
        public bool OutputPolling(ref int intStatus)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            OutputStatusInfo pollingInfo = OutputLinker.OutputStatusCheck(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                TI.Save();
                Logger.WriteLog(errMsg, TI.GetProcessWording(), TI);
                return false;
            }

            switch (pollingInfo.OpCond)
            {
                case global::WSISmile.Business.Enum.OutputStatus.OutputFinished:
                    intStatus = 0;
                    break;
                case global::WSISmile.Business.Enum.OutputStatus.Outputing:
                    intStatus = 1;
                    break;
                default:
                    blnRet = false;
                    TI.ErrorMsgList.Add("出力処理が異常終了しました。" + "エラー番号：" + ((int)pollingInfo.OpCond).ToString());
                    break;
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 出力処理のポーリング : OutputPolling

        #region 受入成功件数の取得 : GetAcceptCount
        /// <summary>
        /// 受入成功件数の取得
        /// </summary>
        /// <param name="intCnt">受入に成功したデータ件数</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "受入に成功したデータ件数を返す。")]
        public bool GetAcceptCount(ref int intCnt)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            try
            {
                // *** 外部受入実行のエラーチェック ***
                List<string> errorsExecute = AcceptLinker.HasProcessErrors(TI, ref errMsg);
                if (errorsExecute.Count > 0)
                {
                    string msg = "外部受入実行が完了しましたが、以下のエラーが発生しています。";
                    TI.ErrorMsgList.Add(msg);
                    TI.ErrorMsgList.AddRange(errorsExecute);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    //return false;
                }

                // *** 外部受入成功件数 ***
                intCnt = TI.Accept.TotalCount - errorsExecute.Count;
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "受入件数：受入件数が取得できませんでした。管理者にご連絡ください。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 受入成功件数の取得 : GetAcceptCount

        #region 出力成功件数の取得 : GetOutputCount
        /// <summary>
        /// 出力成功件数の取得
        /// </summary>
        /// <param name="intCnt">出力に成功したデータ件数</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "出力に成功したデータ件数を返す。")]
        public bool GetOutputCount(ref int intCnt)
        {
            bool blnRet = true;

            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            try
            {
                // 外部出力終了状態.
                OutputStatusInfo finishInfo = OutputLinker.OutputStatusCheck(TI, ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add(errMsg);
                    TI.Save();
                    Logger.WriteLog(errMsg, TI.GetProcessWording(), TI);
                    return false;
                }
                intCnt = finishInfo.TotalProCnt;

                // 月次勤怠File Id.
                OutputLinker.GetOutputTempFileId(TI, ref errMsg);
                if (TI.Output.FileId == "" || errMsg != "")
                {
                    string msg = "月次勤怠ファイルIDを取得できませんでした。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }

                // 月次勤怠Fileをダウンロードし、サーバーに保存
                OutputLinker.OutputFileDownload(TI, ref errMsg);
                if (TI.Output.File == "" || errMsg != "")
                {
                    string msg = "月次勤怠ファイルダウンロードまたはサーバー保存処理に失敗しました。" + Environment.NewLine + errMsg;
                    TI.ErrorMsgList.Add(msg);
                    TI.Save();
                    Logger.WriteLog(msg, TI.GetProcessWording(), TI);
                    return false;
                }
            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = "出力件数：出力件数が取得できませんでした。管理者にご連絡ください。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 出力成功件数取得 : GetOutputCount

        #region 勤怠実績連携データの取得 : GetWorkResult
        /// <summary>
        /// 勤怠実績連携データの取得
        /// </summary>
        /// <param name="ds">勤怠実績データ</param>
        /// <param name="intStart">取得開始インデックス</param>
        /// <param name="intCnt">取得件数</param>
        /// <returns>True:エラーなし | False:エラー発生</returns>
        [WebMethod(Description = "勤怠実績連携データを取得します。")]
        public bool GetWorkResult(ref DataSet ds, int intStart, int intCnt)
        {
            bool blnRet = true;

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            TI.ErrorMsgList = new List<string>();

            try
            {
                // 外部出力の出力項目設定からSmile側連携DataTableのスキーマを作成する
                SmileConverter smileConverter = new SmileConverter();

                DataTable dtSmile = smileConverter.CreateSmileDataTableSchema(TI);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }

                // 外部出力の結果ファイルを読み込み、Smile側連携DataTableに追加する
                smileConverter.ReadOutputCsvFile(TI, dtSmile, intStart, ref intCnt);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }

                // TODO dtSmile 絞り込み前に一度保存するか?

                // 支払日区分に該当する雇用情報(締め期間)に基づき、対象社員を絞り込む
                smileConverter.FilterByPayEmployment(TI, dtSmile);
                if (TI.ErrorMsgList.Count > 0)
                {
                    TI.Save();
                    return false;
                }

                // Smile側のデータフォーマットに合わせて、出力データを調整する
                smileConverter.ReadyforSmileData(TI, dtSmile);

                ds = new DataSet();
                ds.Tables.Add(dtSmile);

            }
            catch (Exception ex)
            {
                blnRet = false;
                string msg = TI.GetProcessWording() + "：勤怠実績の取得に失敗しました。" + Environment.NewLine + ex.Message;
                TI.ErrorMsgList.Add(msg);
                Logger.WriteLog(msg, TI.GetProcessWording(), TI);
            }

            // タスク情報保存
            TI.Save();

            return blnRet;
        }
        #endregion 勤怠実績連携データの取得 : GetWorkResult

        #region プロセス強制終了
        /// <summary>
        /// 連携強制終了
        /// </summary>
        /// <returns>True：エラーなし / False：エラー発生</returns>
        [WebMethod(Description="Webサービスから実行されている全ての受入処理を中断します。")]
        public bool KillAcceptProcess()
        {
            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            if (TI != null)
            {
                TI.ErrorMsgList = new List<string>();

                // タスク情報クリア
                TI.Clear(ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("マスタ連携の終了処理に失敗しました。" + Environment.NewLine + errMsg);
                    TI.Save();
                    return false;
                }
            }

            return true;
        }

        [WebMethod(Description = "Webサービスから実行されている全ての出力処理を中断します。")]
        public bool KillOutputProcess()
        {
            string errMsg = "";

            TaskInfo TI = TaskInfo.Load(GetContractCode(), GetServerPath());
            if (TI != null)
            {
                TI.ErrorMsgList = new List<string>();

                // タスク情報クリア
                TI.Clear(ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("変動項目取込の終了処理に失敗しました。" + Environment.NewLine + errMsg);
                    TI.Save();
                    return false;
                }
            }

            return true;
        }
        #endregion プロセス強制終了

        #endregion Webメソッド

        #region 情報取得メソッド

        #region 契約コードを取得 : GetContractCode
        /// <summary>
        /// 契約コードを取得
        /// </summary>
        /// <returns></returns>
        private string GetContractCode()
        {
            if (this.Context.Request["cid"] != null)
            {
                return this.Context.Request["cid"].ToString();
            }

            return string.Empty;
        }
        #endregion

        #region 契約パスワードを取得 : GetContractPassword
        /// <summary>
        /// 契約パスワードを取得
        /// </summary>
        /// <returns></returns>
        private string GetContractPassword()
        {
            if (this.Context.Request["pwd"] != null)
            {
                return this.Context.Request["pwd"].ToString();
            }

            return string.Empty;
        }
        #endregion

        #region サーバーの物理パスを取得 : GetServerPath
        /// <summary>
        /// サーバーの物理パスを取得
        /// </summary>
        /// <returns></returns>
        private string GetServerPath()
        {
            return HttpContext.Current.Server.MapPath("./");
        }
        #endregion

        #endregion 情報取得メソッド
    }
}
