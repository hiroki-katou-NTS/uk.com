using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Link;
using WSISmile.Business.Log;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// 外部受入-組織情報カテゴリ
    /// </summary>
    public class WorkplaceInfo : AcceptCategoryBase
    {
        public WorkplaceInfo() { }

        #region CONST
        /// <summary>
        /// 階層レベル最大値:10
        /// </summary>
        public const int MAX_LEVEL = 10;

        /// <summary>
        /// スタートレベル (001)
        /// </summary>
        private const int LEVEL_START = 1;
        #endregion CONST

        /// <summary>
        /// Smile組織情報-階層再構築
        /// </summary>
        /// <param name="dsSmile">Smile組織情報</param>
        /// <param name="TI">タスク情報</param>
        /// <remarks>Smile組織情報の階層抜きを補正する(階層レベル再設定)</remarks>
        private void RebuildSmileOrganization(DataSet dsSmile, TaskInfo TI)
        {
            #region Smile組織情報-階層再構築 : RebuildSmileOrganization
            Dictionary<int, int> levelSorter = new Dictionary<int, int>();
            // 階層レベル[1]
            levelSorter.Add(1, 0);

            try
            {
                dsSmile.Tables[0].Columns.Add(SmileOrganizationItem.SORT, typeof(Int32));

                #region Sort補正前
                dsSmile.Tables[0].DefaultView.Sort = SmileOrganizationItem.COMPANY_CD + ", " +
                                                     SmileOrganizationItem.START_DAY + ", " +
                                                     SmileOrganizationItem.LEVEL + ", " +
                                                     SmileOrganizationItem.HIGHER_ORGANIZT_CD + ", " +
                                                     SmileOrganizationItem.ORGANIZATION_CD;
                #endregion Sort補正前

                DataTable dtSmile = dsSmile.Tables[0].DefaultView.ToTable();

                foreach (DataRow drChild in dtSmile.Rows)
                {
                    if (drChild[SmileOrganizationItem.LEVEL] != null)
                    {
                        if (drChild[SmileOrganizationItem.LEVEL].ToString() != "1")
                        {
                            #region 階層レベル[1]以外
                            foreach (DataRow drParent in dtSmile.Rows)
                            {
                                bool found = false;

                                // 子の上位組織コード＝親の組織コード　⇒　親の階層レベル直下(+1)を子の階層レベルとする
                                if (drChild[SmileOrganizationItem.START_DAY] == drParent[SmileOrganizationItem.START_DAY] &&
                                    drChild[SmileOrganizationItem.HIGHER_ORGANIZT_CD] == drParent[SmileOrganizationItem.ORGANIZATION_CD])
                                {
                                    drChild[SmileOrganizationItem.LEVEL] = int.Parse(drParent[SmileOrganizationItem.LEVEL].ToString()) + 1;
                                    found = true;
                                    break;
                                }
                                // 見つからなかった場合、階層レベル[1]
                                if (!found)
                                {
                                    drChild[SmileOrganizationItem.LEVEL] = 1;
                                }

                                int level = int.Parse(drChild[SmileOrganizationItem.LEVEL].ToString());
                                int sort = 0;
                                if (levelSorter.ContainsKey(level))
                                {
                                    sort = levelSorter[level];
                                }
                                else
                                {
                                    levelSorter.Add(level, sort);
                                }

                                drChild[SmileOrganizationItem.SORT] = sort;

                                levelSorter[level] = levelSorter[level] + 1;
                            }
                            #endregion
                        }
                        else
                        {
                            #region 階層レベル[1]
                            drChild[SmileOrganizationItem.SORT] = levelSorter[1];
                            levelSorter[1] = levelSorter[1] + 1;
                            #endregion
                        }
                    }
                }

                #region Sort補正後
                dtSmile.DefaultView.Sort = SmileOrganizationItem.COMPANY_CD + ", " + 
                                           SmileOrganizationItem.START_DAY + ", " + 
                                           SmileOrganizationItem.LEVEL + ", " + 
                                           SmileOrganizationItem.HIGHER_ORGANIZT_CD + ", " + 
                                           SmileOrganizationItem.SORT;
                #endregion Sort補正後
            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("Smile組織情報-階層再構築処理に例外が発生しました。" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// Smile組織情報を 階層再構築 ＆ 編集 ＆ List.へ変換
        /// </summary>
        /// <param name="dsSmile">Smile組織情報</param>
        /// <param name="TI">タスク情報</param>
        /// <returns>Smile組織情報List.</returns>
        public List<SmileOrganization> SmileDataToList(DataSet dsSmile, TaskInfo TI)
        {
            #region Smile組織情報を 階層再構築 ＆ 編集 ＆ List.へ変換 : SmileDataToList
            // Smile組織情報-階層再構築
            this.RebuildSmileOrganization(dsSmile, TI);

            List<SmileOrganization> list = new List<SmileOrganization>();

            foreach (DataRow drSmile in dsSmile.Tables[0].Rows)
            {
                SmileOrganization smileOrganization = new SmileOrganization();

                // 会社コード
                smileOrganization.CompanyCd = TI.CompCode;

                // 組織コード
                smileOrganization.OrganizationCd = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZATION_CD]);

                // 発令年月日
                smileOrganization.StartDay = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.START_DAY]);

                // 終了年月日
                if (drSmile[SmileOrganizationItem.END_DAY].ToString() == "99999999")
                {
                    smileOrganization.EndDay = new DateTime(9999, 12, 31).ToString(Format.Date);
                }
                else
                {
                    smileOrganization.EndDay = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.END_DAY]);
                }

                // 上位組織コード
                smileOrganization.HigherOrganiztCd = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.HIGHER_ORGANIZT_CD]);

                if (!Toolbox.IsNumber(drSmile[SmileOrganizationItem.LEVEL].ToString()))
                {
                    TI.ErrorMsgList.Add(this.GetErrorMessage(smileOrganization, "階層レベルに数字ではない値が含まれています。"));
                    continue;
                }

                // 階層レベル
                smileOrganization.Level = int.Parse(Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.LEVEL]));

                // 正式組織名
                smileOrganization.OrganiztNameOfficial = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL]);

                // 組織名
                smileOrganization.OrganiztName = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME]);

                // 組織略称
                smileOrganization.OrganiztNameSimple = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME_SIMPLE]);

                list.Add(smileOrganization);
            }

            return list;
            #endregion
        }

        /// <summary>
        /// 職場情報の内部階層CD[1～10]を設定する
        /// </summary>
        /// <param name="listSmileOrganization">Smile組織情報List.</param>
        /// <param name="TI"></param>
        public void SetWorkplaceInLevelCd(List<SmileOrganization> listSmileOrganization, TaskInfo TI)
        {
            #region 職場情報の内部階層CD[1～10]を設定する : SetWorkplaceInLevelCd
            // 前回情報
            SmileOrganization previous = new SmileOrganization();

            try
            {
                foreach (SmileOrganization smileOrganization in listSmileOrganization)
                {
                    // TODO 勤次郎の締め開始日よりも前の発令年月日("yyyy/MM/dd")を連携されませんでした。
                    if (smileOrganization.Level > MAX_LEVEL)
                    {
                        // 10階層以上の場合に、内部階層CD[1]をクリア
                        smileOrganization.InlevelCds[0] = "";
                        continue;
                    }

                    if (smileOrganization.StartDay != previous.StartDay)
                    {
                        // 発令年月日が異なる場合、前回情報をクリア
                        previous = new SmileOrganization();
                    }

                    if (smileOrganization.HigherOrganiztCd == "")
                    {
                        #region 階層レベル[1] (上位組織コードが存在しない)
                        if (smileOrganization.Level == previous.Level)
                        {
                            // 階層レベル[1]の場合に、カウントアップ
                            smileOrganization.InlevelCds[0] = WorkplaceInfo.IntegerToInlevelCd((int.Parse(previous.InlevelCds[0]) + 1));
                        }
                        else
                        {
                            // First.
                            smileOrganization.InlevelCds[0] = WorkplaceInfo.IntegerToInlevelCd(LEVEL_START);
                        }
                        #endregion
                    }
                    else
                    {
                        #region 階層レベル[1]以降
                        if (!SearchHigherOrganization(smileOrganization, listSmileOrganization))
                        {
                            // 内部階層CD[1]をクリア
                            smileOrganization.InlevelCds[0] = "";
                            TI.ErrorMsgList.Add(this.GetErrorMessage(smileOrganization, "上位組織コードのデータが存在しません。"));
                            continue;
                        }

                        int index = smileOrganization.Level - 1;

                        if (smileOrganization.HigherOrganiztCd == previous.HigherOrganiztCd)
                        {
                            // 上位組織が同じの場合に、カウントアップ
                            smileOrganization.InlevelCds[index] = WorkplaceInfo.IntegerToInlevelCd((int.Parse(previous.InlevelCds[index]) + 1));
                        }
                        else
                        {
                            // 上位組織が異なる場合に、リセット
                            smileOrganization.InlevelCds[index] = WorkplaceInfo.IntegerToInlevelCd(LEVEL_START);
                        }
                        #endregion
                    }

                    // 前回情報を保存
                    previous = smileOrganization.Clone();

                    // 内部階層コードを設定
                    for (int i = 0; i < MAX_LEVEL; i++)
                    {
                        smileOrganization.InlevelCd += smileOrganization.InlevelCds[i];
                    }
                }

            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("職場情報の内部階層CD[1～10]設定処理に例外が発生しました。" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// 指定組織の上位組織を検索し、内部階層CD[1～10]をコピー
        /// </summary>
        /// <param name="searchOrganization">検索対象組織</param>
        /// <param name="listSmileOrganization">全組織</param>
        /// <returns>true:見つかった／false:見つかっていない</returns>
        private bool SearchHigherOrganization(SmileOrganization searchOrganization, List<SmileOrganization> listSmileOrganization)
        {
            #region 指定組織の上位組織を検索し、内部階層CD[1～10]をコピー : SearchHigherOrganization
            if (searchOrganization.Level == 1)
            {
                // TOP階層レベルの場合
                return true;
            }

            foreach (SmileOrganization smileOrganization in listSmileOrganization)
            {
                /*
                 * 下記条件を満たす場合に、上位組織と見なし上位組織の内部階層CD[1]～内部階層CD[10]を検索対象組織へコピーする
                 *  1) 発令年月日が同じ
                 *  2) 検索組織の上位組織コード ＝ 当該組織の組織コード
                 *  3) 検索組織の階層レベル一つ以上 ＝ 当該組織の階層レベル
                 *  4) 当該組織の内部階層CD[1]が空ではない
                */

                if (smileOrganization.StartDay == searchOrganization.StartDay &&
                    smileOrganization.OrganizationCd == searchOrganization.HigherOrganiztCd &&
                    smileOrganization.Level == searchOrganization.Level - 1 &&
                    smileOrganization.InlevelCds[0] != "")
                {
                    searchOrganization.InlevelCds = new List<string>(smileOrganization.InlevelCds);
                    return true;
                }
            }

            return false;
            #endregion
        }

        /// <summary>
        /// Smile組織情報List.を連携DataTableへ変換
        /// </summary>
        /// <param name="listSmile"></param>
        /// <returns></returns>
        public DataTable ListToSmileLinkTable(List<SmileOrganization> listSmile)
        {
            #region Smile組織情報List.を連携DataTableへ変換 : ListToSmileLinkTable
            // Smile側連携DataTable
            DataTable dtSmile = SmileOrganization.DefineSmileDataTable();

            foreach (SmileOrganization smileOrganization in listSmile)
            {
                DataRow drSmile = dtSmile.NewRow();

                // 開始日
                drSmile[SmileOrganizationItem.START_DAY] = smileOrganization.StartDay;

                // 終了日
                drSmile[SmileOrganizationItem.END_DAY] = smileOrganization.EndDay;

                // 職場コード
                drSmile[SmileOrganizationItem.ORGANIZATION_CD] = smileOrganization.OrganizationCd;

                // 職場名称
                drSmile[SmileOrganizationItem.ORGANIZT_NAME] = smileOrganization.OrganiztName;

                // 職場略称
                drSmile[SmileOrganizationItem.ORGANIZT_NAME_SIMPLE] = smileOrganization.OrganiztNameSimple;

                // 職場総称
                drSmile[SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL] = smileOrganization.OrganiztNameOfficial;

                // 職場階層コード
                drSmile[SmileOrganizationItem.INLEVEL_CD] = smileOrganization.InlevelCd;

                #region 職場階層コード 1 ～ 職場階層コード 10
                // 職場階層コード 1
                drSmile[SmileOrganizationItem.INLEVEL_CD_1] = smileOrganization.InlevelCds[0];

                // 職場階層コード 2
                drSmile[SmileOrganizationItem.INLEVEL_CD_2] = smileOrganization.InlevelCds[1];

                // 職場階層コード 3
                drSmile[SmileOrganizationItem.INLEVEL_CD_3] = smileOrganization.InlevelCds[2];

                // 職場階層コード 4
                drSmile[SmileOrganizationItem.INLEVEL_CD_4] = smileOrganization.InlevelCds[3];

                // 職場階層コード 5
                drSmile[SmileOrganizationItem.INLEVEL_CD_5] = smileOrganization.InlevelCds[4];

                // 職場階層コード 6
                drSmile[SmileOrganizationItem.INLEVEL_CD_6] = smileOrganization.InlevelCds[5];

                // 職場階層コード 7
                drSmile[SmileOrganizationItem.INLEVEL_CD_7] = smileOrganization.InlevelCds[6];

                // 職場階層コード 8
                drSmile[SmileOrganizationItem.INLEVEL_CD_8] = smileOrganization.InlevelCds[7];

                // 職場階層コード 9
                drSmile[SmileOrganizationItem.INLEVEL_CD_9] = smileOrganization.InlevelCds[8];

                // 職場階層コード 10
                drSmile[SmileOrganizationItem.INLEVEL_CD_10] = smileOrganization.InlevelCds[9];
                #endregion 職場階層コード 1 ～ 職場階層コード 10

                dtSmile.Rows.Add(drSmile);
            }

            return dtSmile;
            #endregion
        }

        /// <summary>
        /// 職場階層コードフォーマット
        /// </summary>
        /// <param name="inlevelCd">職場階層コード</param>
        /// <returns></returns>
        private static string IntegerToInlevelCd(int inlevelCd)
        {
            #region 職場階層コードフォーマット : IntegerToInlevelCd
            return inlevelCd.ToString().PadLeft(3, '0');
            #endregion
        }

        /// <summary>
        /// 職場エラーメッセージの作成
        /// </summary>
        /// <returns>エラーメッセージ</returns>
        private string GetErrorMessage(SmileOrganization smileOrganization, string appendMsg)
        {
            #region 職場エラーメッセージの作成 : GetErrorMessage
            return 
                "組織情報：会社コード=" + smileOrganization.CompanyCd + 
                " 組織コード=" + smileOrganization.OrganizationCd + 
                " 組織名=" + smileOrganization.OrganiztName + 
                " ⇒ " + appendMsg;
            #endregion
        }
    }
}
