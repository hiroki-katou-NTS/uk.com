using System;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 外部出力社員情報
    /// </summary>
    public class OutputEmployeeInfo
    {
        #region コンストラクタ
        /// <summary>
        /// 外部出力社員情報
        /// </summary>
        /// <param name="employeeCd">社員CD</param>
        /// <param name="processMonth">処理年月</param>
        public OutputEmployeeInfo(string employeeCd, string processMonth)
        {
            this._employeeCd = employeeCd;
            this._processMonth = processMonth;
        }
        #endregion

        #region 内部変数
        private string _employeeCd = "";

        private string _processMonth = "";
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 社員CD
        /// </summary>
        public string EmployeeCd
        {
            get { return _employeeCd; }
            set { _employeeCd = value; }
        }

        /// <summary>
        /// 処理年月(締め月の当月 or 前月) (yyyyMM)
        /// </summary>
        public string ProcessMonth
        {
            get { return _processMonth; }
            set { _processMonth = value; }
        }
        #endregion プロパティ

        #region [override] Method
        /// <summary>
        /// Equals [override]
        /// </summary>
        /// <param name="obj">obj</param>
        /// <returns></returns>
        public override bool Equals(object obj)
        {
            if (obj == null || this.GetType() != obj.GetType())
            {
                return false;
            }

            OutputEmployeeInfo employee = (OutputEmployeeInfo)obj;
            return (
                //this._employeeCd == employee._employeeCd &&
                this._employeeCd.Contains(employee._employeeCd) && 
                this._processMonth == employee._processMonth);
        }

        /// <summary>
        /// GetHashCode [override]
        /// </summary>
        /// <returns></returns>
        public override int GetHashCode()
        {
            return this.GetHashCode();
        }
        #endregion
    }
}
