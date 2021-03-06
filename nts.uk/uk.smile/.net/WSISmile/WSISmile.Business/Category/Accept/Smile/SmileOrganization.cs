using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// SmilegD๎๑
    /// </summary>
    public class SmileOrganization
    {
        #region RXgN^
        public SmileOrganization()
        {
            // เKwCD[1]`เKwCD[10]๐๚ป
            this._inlevelCds = new List<string>(WorkplaceInfo.MAX_LEVEL);

            for (int i = 1; i <= WorkplaceInfo.MAX_LEVEL; i++)
            {
                this._inlevelCds.Add(string.Empty);
            }
        }
        #endregion

        #region เฯ
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
        #endregion เฯ

        #region vpeB
        /// <summary>
        /// ๏ะR[h
        /// </summary>
        public string CompanyCd
        {
            get { return _companyCd; }
            set { _companyCd = value; }
        }

        /// <summary>
        /// gDR[h
        /// </summary>
        public string OrganizationCd
        {
            get { return _organizationCd; }
            set { _organizationCd = value; }
        }

        /// <summary>
        /// ญ฿N๚
        /// </summary>
        public string StartDay
        {
            get { return _startDay; }
            set { _startDay = value; }
        }

        /// <summary>
        /// IนN๚
        /// </summary>
        public string EndDay
        {
            get { return _endDay; }
            set { _endDay = value; }
        }

        /// <summary>
        /// ใสgDR[h
        /// </summary>
        public string HigherOrganiztCd
        {
            get { return _higherOrganiztCd; }
            set { _higherOrganiztCd = value; }
        }

        /// <summary>
        /// Kwx
        /// </summary>
        public int Level
        {
            get { return _level; }
            set { _level = value; }
        }

        /// <summary>
        /// ณฎgDผ
        /// </summary>
        public string OrganiztNameOfficial
        {
            get { return _organiztNameOfficial; }
            set { _organiztNameOfficial = value; }
        }

        /// <summary>
        /// gDผ
        /// </summary>
        public string OrganiztName
        {
            get { return _organiztName; }
            set { _organiztName = value; }
        }

        /// <summary>
        /// gDชฬ
        /// </summary>
        public string OrganiztNameSimple
        {
            get { return _organiztNameSimple; }
            set { _organiztNameSimple = value; }
        }

        /// <summary>
        /// เKwR[h
        /// </summary>
        public string InlevelCd
        {
            get { return _inlevelCd; }
            set { _inlevelCd = value; }
        }

        /// <summary>
        /// เKwCD[1]`เKwCD[10]
        /// </summary>
        public List<string> InlevelCds
        {
            get { return _inlevelCds; }
            set { _inlevelCds = value; }
        }
        #endregion vpeB

        #region ศีRs[
        /// <summary>
        /// ศีRs[
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

        #region SmileคAgDataTableฬXL[}๐่`
        /// <summary>
        /// SmileคAgDataTableฬXL[}๐่`
        /// </summary>
        /// <returns></returns>
        public static DataTable DefineSmileDataTable()
        {
            // SmileคAgDataTable
            DataTable dtSchema = new DataTable();

            // Jn๚
            dtSchema.Columns.Add(SmileOrganizationItem.START_DAY, System.Type.GetType(DataType.String));

            // Iน๚
            dtSchema.Columns.Add(SmileOrganizationItem.END_DAY, System.Type.GetType(DataType.String));

            // E๊R[h
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZATION_CD, System.Type.GetType(DataType.String));

            // E๊ผฬ
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME, System.Type.GetType(DataType.String));

            // E๊ชฬ
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME_SIMPLE, System.Type.GetType(DataType.String));

            // E๊ฬ
            dtSchema.Columns.Add(SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL, System.Type.GetType(DataType.String));

            // E๊KwR[h
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD, System.Type.GetType(DataType.String));

            #region E๊KwR[h 1 ` E๊KwR[h 10
            // E๊KwR[h 1
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_1, System.Type.GetType(DataType.String));

            // E๊KwR[h 2
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_2, System.Type.GetType(DataType.String));

            // E๊KwR[h 3
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_3, System.Type.GetType(DataType.String));

            // E๊KwR[h 4
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_4, System.Type.GetType(DataType.String));

            // E๊KwR[h 5
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_5, System.Type.GetType(DataType.String));

            // E๊KwR[h 6
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_6, System.Type.GetType(DataType.String));

            // E๊KwR[h 7
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_7, System.Type.GetType(DataType.String));

            // E๊KwR[h 8
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_8, System.Type.GetType(DataType.String));

            // E๊KwR[h 9
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_9, System.Type.GetType(DataType.String));

            // E๊KwR[h 10
            dtSchema.Columns.Add(SmileOrganizationItem.INLEVEL_CD_10, System.Type.GetType(DataType.String));
            #endregion E๊KwR[h 1 ` E๊KwR[h 10

            return dtSchema;
        }
        #endregion
    }

    /// <summary>
    /// SmilegD๎๑-ฺ่`
    /// </summary>
    public class SmileOrganizationItem
    {
        #region SmilegD๎๑-ฺ่`
        /// <summary>
        /// ๏ะR[h 001
        /// </summary>
        public const string COMPANY_CD = "001";

        /// <summary>
        /// gDR[h 002
        /// </summary>
        public const string ORGANIZATION_CD = "002";

        /// <summary>
        /// ญ฿N๚ 003
        /// </summary>
        public const string START_DAY = "003";

        /// <summary>
        /// IนN๚ 005
        /// </summary>
        public const string END_DAY = "005";

        /// <summary>
        /// ใสgDR[h 006
        /// </summary>
        public const string HIGHER_ORGANIZT_CD = "006";

        /// <summary>
        /// Kwx 007
        /// </summary>
        public const string LEVEL = "007";

        /// <summary>
        /// ณฎgDผ 009
        /// </summary>
        public const string ORGANIZT_NAME_OFFICIAL = "009";

        /// <summary>
        /// gDผ 010
        /// </summary>
        public const string ORGANIZT_NAME = "010";

        /// <summary>
        /// gDชฬ 011
        /// </summary>
        public const string ORGANIZT_NAME_SIMPLE = "011";

        /// <summary>
        /// เKwR[h
        /// </summary>
        public const string INLEVEL_CD = "056";

        #region เKwR[h 1 ` เKwR[h 10
        /// <summary>
        /// เKwR[h 1
        /// </summary>
        public const string INLEVEL_CD_1 = "061";

        /// <summary>
        /// เKwR[h 2
        /// </summary>
        public const string INLEVEL_CD_2 = "062";

        /// <summary>
        /// เKwR[h 3
        /// </summary>
        public const string INLEVEL_CD_3 = "063";

        /// <summary>
        /// เKwR[h 4
        /// </summary>
        public const string INLEVEL_CD_4 = "064";

        /// <summary>
        /// เKwR[h 5
        /// </summary>
        public const string INLEVEL_CD_5 = "065";

        /// <summary>
        /// เKwR[h 6
        /// </summary>
        public const string INLEVEL_CD_6 = "066";

        /// <summary>
        /// เKwR[h 7
        /// </summary>
        public const string INLEVEL_CD_7 = "067";

        /// <summary>
        /// เKwR[h 8
        /// </summary>
        public const string INLEVEL_CD_8 = "068";

        /// <summary>
        /// เKwR[h 9
        /// </summary>
        public const string INLEVEL_CD_9 = "069";

        /// <summary>
        /// เKwR[h 10
        /// </summary>
        public const string INLEVEL_CD_10 = "070";
        #endregion เKwR[h 1 ` เKwR[h 10

        /// <summary>
        /// ภั
        /// </summary>
        public const string SORT = "Sort";
        #endregion SmilegD๎๑-ฺ่`
    }
}
