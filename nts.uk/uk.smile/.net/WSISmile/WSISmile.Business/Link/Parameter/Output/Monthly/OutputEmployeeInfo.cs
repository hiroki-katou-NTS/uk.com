using System;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �O���o�͎Ј����
    /// </summary>
    public class OutputEmployeeInfo
    {
        #region �R���X�g���N�^
        /// <summary>
        /// �O���o�͎Ј����
        /// </summary>
        /// <param name="employeeCd">�Ј�CD</param>
        /// <param name="processMonth">�����N��</param>
        public OutputEmployeeInfo(string employeeCd, string processMonth)
        {
            this._employeeCd = employeeCd;
            this._processMonth = processMonth;
        }
        #endregion

        #region �����ϐ�
        private string _employeeCd = "";

        private string _processMonth = "";
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �Ј�CD
        /// </summary>
        public string EmployeeCd
        {
            get { return _employeeCd; }
            set { _employeeCd = value; }
        }

        /// <summary>
        /// �����N��(���ߌ��̓��� or �O��) (yyyyMM)
        /// </summary>
        public string ProcessMonth
        {
            get { return _processMonth; }
            set { _processMonth = value; }
        }
        #endregion �v���p�e�B

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
