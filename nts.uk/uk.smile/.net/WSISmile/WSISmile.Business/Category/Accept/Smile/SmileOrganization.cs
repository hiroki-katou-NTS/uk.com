using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// Smile組織情報
    /// </summary>
    public class SmileOrganization
    {
        #region コンストラクタ
        public SmileOrganization()
        {
            // 内部階層CD[1]～内部階層CD[10]を初期化
            this._inlevelCds = new List<string>(WorkplaceInfo.MAX_LEVEL);

            for (int i = 1; i <= WorkplaceInfo.MAX_LEVEL; i++)
            {
                this._inlevelCds.Add(string.Empty);
            }
        }
        #endregion

        #region 内部変数
        private string _companyCd = "";

        private string _organizationCd = "";

        private string _startDay = "";

        private string _endDay = "";

        private string _higherOrganiztCd = "";

        private int _level = 0;

        private string _organiztNameOfficial = "";

        private string _organiztName = "";

        private string _organiztNameSimple = "";

        private string _inlevelCd = "";

        private List<string> _inlevelCds = new List<string>(10);
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 会社コード
        /// </summary>
        public string CompanyCd
        {
            get { return _companyCd; }
            set { _companyCd = value; }
        }

        /// <summary>
        /// 組織コード
        /// </summary>
        public string OrganizationCd
        {
            get { return _organizationCd; }
            set { _organizationCd = value; }
        }

        /// <summary>
        /// 発令年月日
        /// </summary>
        public string StartDay
        {
            get { return _startDay; }
            set { _startDay = value; }
        }

        /// <summary>
        /// 終了年月日
        /// </summary>
        public string EndDay
        {
            get { return _endDay; }
            set { _endDay = value; }
        }

        /// <summary>
        /// 上位組織コード
        /// </summary>
        public string HigherOrganiztCd
        {
            get { return _higherOrganiztCd; }
            set { _higherOrganiztCd = value; }
        }

        /// <summary>
        /// 階層レベル
        /// </summary>
        public int Level
        {
            get { return _level; }
            set { _level = value; }
        }

        /// <summary>
        /// 正式組織名
        /// </summary>
        public string OrganiztNameOfficial
        {
            get { return _organiztNameOfficial; }
            set { _organiztNameOfficial = value; }
        }

        /// <summary>
        /// 組織名
        /// </summary>
        public string OrganiztName
        {
            get { return _organiztName; }
            set { _organiztName = value; }
        }

        /// <summary>
        /// 組織略称
        /// </summary>
        public string OrganiztNameSimple
        {
            get { return _organiztNameSimple; }
            set { _organiztNameSimple = value; }
        }

        /// <summary>
        /// 内部階層コード
        /// </summary>
        public string InlevelCd
        {
            get { return _inlevelCd; }
            set { _inlevelCd = value; }
        }

        /// <summary>
        /// 内部階層CD[1]～内部階層CD[10]
        /// </summary>
        public List<string> InlevelCds
        {
            get { return _inlevelCds; }
            set { _inlevelCds = value; }
        }
        #endregion プロパティ

        #region 簡易コピー
        /// <summary>
        /// 簡易コピー
        /// </summary>
        /// <returns></returns>
        public SmileOrganization Clone()
        {
            SmileOrganization cloned = MemberwiseClone() as SmileOrganization;

            if (this._inlevelCds != null)
            {
                cloned._inlevelCds = new List<string>(this._inlevelCds);
            }

            return cloned;
        }
        #endregion

        #region Smile側連携DataTableのスキーマを定義
        /// <summary>
        /// Smile側連携DataTableのスキーマを定義
        /// </summary>
        /// <returns></returns>
        public static DataTable DefineSmileDataTable()
        {
            // Smile側連携DataTable
            DataTable dtSchema = new DataTable();

            // 開始日
            dtSchema.Columns.Add(SmileOrganizationItem.START_DAY, System.Type.GetType(DataType.String));

            // 終了日
            dtSchema.Columns.Add(SmileOrganizationItem.END_DAY, System.Type.GetType(DataType.String));

            // 職場コード
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZATION_CD, System.Type.GetType(DataType.String));

            // 職場名称
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME, System.Type.GetType(DataType.String));

            // 職場略称
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME_SIMPLE, System.Type.GetType(DataType.String));

            // 職場総称
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL, System.Type.GetType(DataType.String));

            // 職場階層コード
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD, System.Type.GetType(DataType.String));

            #region 職場階層コード 1 ～ 職場階層コード 10
            // 職場階層コード 1
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_1, System.Type.GetType(DataType.String));

            // 職場階層コード 2
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_2, System.Type.GetType(DataType.String));

            // 職場階層コード 3
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_3, System.Type.GetType(DataType.String));

            // 職場階層コード 4
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_4, System.Type.GetType(DataType.String));

            // 職場階層コード 5
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_5, System.Type.GetType(DataType.String));

            // 職場階層コード 6
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_6, System.Type.GetType(DataType.String));

            // 職場階層コード 7
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_7, System.Type.GetType(DataType.String));

            // 職場階層コード 8
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_8, System.Type.GetType(DataType.String));

            // 職場階層コード 9
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_9, System.Type.GetType(DataType.String));

            // 職場階層コード 10
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_10, System.Type.GetType(DataType.String));
            #endregion 職場階層コード 1 ～ 職場階層コード 10

            return dtSchema;
        }
        #endregion
    }

    /// <summary>
    /// Smile組織情報-項目定義
    /// </summary>
    public class SmileOrganizationItem
    {
        #region Smile組織情報-項目定義
        /// <summary>
        /// 会社コード 001
        /// </summary>
        public const string COMPANY_CD = "001";

        /// <summary>
        /// 組織コード 002
        /// </summary>
        public const string ORGANIZATION_CD = "002";

        /// <summary>
        /// 発令年月日 003
        /// </summary>
        public const string START_DAY = "003";

        /// <summary>
        /// 終了年月日 005
        /// </summary>
        public const string END_DAY = "005";

        /// <summary>
        /// 上位組織コード 006
        /// </summary>
        public const string HIGHER_ORGANIZT_CD = "006";

        /// <summary>
        /// 階層レベル 007
        /// </summary>
        public const string LEVEL = "007";

        /// <summary>
        /// 正式組織名 009
        /// </summary>
        public const string ORGANIZT_NAME_OFFICIAL = "009";

        /// <summary>
        /// 組織名 010
        /// </summary>
        public const string ORGANIZT_NAME = "010";

        /// <summary>
        /// 組織略称 011
        /// </summary>
        public const string ORGANIZT_NAME_SIMPLE = "011";

        /// <summary>
        /// 内部階層コード
        /// </summary>
        public const string INLEVEL_CD = "056";

        #region 内部階層コード 1 ～ 内部階層コード 10
        /// <summary>
        /// 内部階層コード 1
        /// </summary>
        public const string INLEVEL_CD_1 = "061";

        /// <summary>
        /// 内部階層コード 2
        /// </summary>
        public const string INLEVEL_CD_2 = "062";

        /// <summary>
        /// 内部階層コード 3
        /// </summary>
        public const string INLEVEL_CD_3 = "063";

        /// <summary>
        /// 内部階層コード 4
        /// </summary>
        public const string INLEVEL_CD_4 = "064";

        /// <summary>
        /// 内部階層コード 5
        /// </summary>
        public const string INLEVEL_CD_5 = "065";

        /// <summary>
        /// 内部階層コード 6
        /// </summary>
        public const string INLEVEL_CD_6 = "066";

        /// <summary>
        /// 内部階層コード 7
        /// </summary>
        public const string INLEVEL_CD_7 = "067";

        /// <summary>
        /// 内部階層コード 8
        /// </summary>
        public const string INLEVEL_CD_8 = "068";

        /// <summary>
        /// 内部階層コード 9
        /// </summary>
        public const string INLEVEL_CD_9 = "069";

        /// <summary>
        /// 内部階層コード 10
        /// </summary>
        public const string INLEVEL_CD_10 = "070";
        #endregion 内部階層コード 1 ～ 内部階層コード 10

        /// <summary>
        /// 並び順
        /// </summary>
        public const string SORT = "Sort";
        #endregion Smile組織情報-項目定義
    }
}
