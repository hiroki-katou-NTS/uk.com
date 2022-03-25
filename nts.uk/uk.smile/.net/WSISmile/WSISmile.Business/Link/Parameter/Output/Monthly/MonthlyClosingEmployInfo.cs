using System;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 雇用／締め／処理年月／締め期間情報(支払日区分ごと)
    /// </summary>
    public class MonthlyClosingEmployInfo
    {
        #region 内部変数
        private string _employmentCd = "";

        private int _closureId = 0;

        private int _processMonth = 0;

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 雇用コード
        /// </summary>
        public string EmploymentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// 締めID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }

        /// <summary>
        /// 処理(対象)年月(締め月の当月 or 前月) (yyyyMM)
        /// </summary>
        public int ProcessMonth
        {
            get { return _processMonth; }
            set { _processMonth = value; }
        }

        /// <summary>
        /// 締め開始日
        /// </summary>
        public DateTime StartDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// 締め終了日
        /// </summary>
        public DateTime EndDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion プロパティ
    }
}
