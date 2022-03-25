using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Setting
{
    /// <summary>
    /// Smile連携-外部出力設定情報
    /// </summary>
    public class SmileOutputSetting
    {
        #region Smile連携-外部出力設定情報
        #region コンストラクタ
        public SmileOutputSetting() { }

        public SmileOutputSetting(JObject aJObject)
        {
            if (aJObject["salaryCooperationClassification"] != null)
            {
                this._useDefaultSetCd = Convert.ToBoolean(aJObject.GetValue("salaryCooperationClassification").ToObject<int>());
            }

            if (aJObject["salaryCooperationConditions"] != null)
            {
                this._conditionSetCd = aJObject.GetValue("salaryCooperationConditions").ToObject<string>();
            }

            if (aJObject["monthlyLockClassification"] != null)
            {
                this._checkLockStatus = Convert.ToBoolean(aJObject.GetValue("monthlyLockClassification").ToObject<int>());
            }

            if (aJObject["monthlyApprovalCategory"] != null)
            {
                this._checkApproveStatus = Convert.ToBoolean(aJObject.GetValue("monthlyApprovalCategory").ToObject<int>());
            }

            if (aJObject["linkedPaymentConversion"] != null)
            {
                foreach (JObject payment in aJObject.GetValue("linkedPaymentConversion").ToObject<JArray>())
                {
                    PaymentInfo paymentInfo = new PaymentInfo(payment);
                    _paymentInfoList.Add(paymentInfo);
                }
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private bool _useDefaultSetCd = false;

        private string _conditionSetCd = string.Empty;

        private bool _checkLockStatus = false;

        private bool _checkApproveStatus = false;

        private List<PaymentInfo> _paymentInfoList = new List<PaymentInfo>();
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 変動項目区分(デフォルトの外部出力条件コードを使うか)
        /// </summary>
        public bool UseDefaultSetCd
        {
            get { return _useDefaultSetCd; }
            set { _useDefaultSetCd = value; }
        }

        /// <summary>
        /// 外部出力条件コード
        /// </summary>
        public string ConditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }

        /// <summary>
        /// 『月別実績修正のロック』チェック区分
        /// </summary>
        public bool CheckLockStatus
        {
            get { return _checkLockStatus; }
            set { _checkLockStatus = value; }
        }

        /// <summary>
        /// 『月別実績の確認』チェック区分
        /// </summary>
        public bool CheckApproveStatus
        {
            get { return _checkApproveStatus; }
            set { _checkApproveStatus = value; }
        }

        /// <summary>
        /// 支払日区分情報List.
        /// </summary>
        public List<PaymentInfo> PaymentInfoList
        {
            get { return _paymentInfoList; }
            set { _paymentInfoList = value; }
        }
        #endregion
        #endregion
    }

    /// <summary>
    /// 支払日区分情報
    /// </summary>
    public class PaymentInfo
    {
        #region 支払日区分情報
        #region コンストラクタ
        public PaymentInfo() { }

        public PaymentInfo(JObject aJObject)
        {
            this._payment = aJObject.GetValue("paymentCode").ToObject<int>();

            foreach (JObject employTargetMonth in aJObject.GetValue("selectiveEmploymentCodes").ToObject<JArray>())
            {
                PayEmployTargetMonthInfo payEmployTargetMonthInfo = new PayEmployTargetMonthInfo(employTargetMonth);
                _payEmpMonthList.Add(payEmployTargetMonthInfo);
            }
        }
        #endregion

        #region 内部変数
        private int _payment = 0;

        private List<PayEmployTargetMonthInfo> _payEmpMonthList = new List<PayEmployTargetMonthInfo>();
        #endregion

        #region プロパティ
        /// <summary>
        /// 支払日区分
        /// </summary>
        public int Payment
        {
            get { return _payment; }
            set { _payment = value; }
        }

        /// <summary>
        /// 支払日区分ごとの雇用／対象月(連動月の設定)情報List.
        /// </summary>
        public List<PayEmployTargetMonthInfo> PayEmployTargetMonthInfoList
        {
            get { return _payEmpMonthList; }
            set { _payEmpMonthList = value; }
        }
        #endregion
        #endregion
    }

    /// <summary>
    /// 支払日区分ごとの雇用／対象月(連動月の設定)情報
    /// </summary>
    public class PayEmployTargetMonthInfo
    {
        #region 支払日区分ごとの雇用／対象月(連動月の設定)情報
        #region コンストラクタ
        public PayEmployTargetMonthInfo() { }

        public PayEmployTargetMonthInfo(JObject aJObject)
        {
            this._employmentCd = aJObject.GetValue("scd").ToObject<string>();

            this._targetMonth = (TargetMonth)System.Enum.ToObject(typeof(TargetMonth), aJObject.GetValue("interlockingMonthAdjustment").ToObject<int>());
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _employmentCd = "";

        private TargetMonth _targetMonth = TargetMonth.Current;
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
        /// 対象月(連動月の設定)
        /// </summary>
        public TargetMonth TargetMonth
        {
            get { return _targetMonth; }
            set { _targetMonth = value; }
        }
        #endregion プロパティ
        #endregion
    }
}
