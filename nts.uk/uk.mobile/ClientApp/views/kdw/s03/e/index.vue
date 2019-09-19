<template>
<div class="kdws03e">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0">
      <!-- E1_1: 戻るボタン -->
      <div class="col-1 pl-1" v-on:click="$close()"><i class="fas fa-arrow-left"></i></div>
      <!-- E1_2: 対象社員名 -->
      <div class="col-6 pl-0 pr-2"><span>{{ empName }}</span></div>
      <!-- E1_3: 対象年月日 -->
      <div class="col-5 pl-0 pr-0"><span>{{ params.date  | date('YYYY年MM月DD日')}}</span></div>
    </div>
  </div>
    <!-- E2_1: エラー内容 -->
    <div>{{'KDWS03_43' | i18n}}</div>
    <div class="row pt-2">
        <!-- E3_1,2: Label コード－ -->
        <div class="col-4">{{'KDWS03_42' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <!-- E3_3: コード -->
        <div class="col-8">{{errorInfo.code}}</div>
    </div>
    <div class="row pt-2">
        <!-- E4_1,2: Label エラー内容－ -->
        <div class="col-4">{{'KDWS03_43' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <!-- E4_3: エラー内容 -->
        <div class="col-8">{{errorInfo.name}}</div>
    </div>
    <div class="row pt-2">
        <!-- E5_1,2: Label メーセージ－ -->
        <div class="col-4">{{'KDWS03_44' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <!-- E5_3: メーセージ -->
        <div class="col-8">{{errorInfo.errMsg}}</div>
    </div>
    <!-- E7_1: 修正ボタン -->
    <div class="fixed-bottom border-0 button-shuusei" v-if="displayE71">
      <button class = 'btn btn-success btn-block rounded-0' v-on:click="editData">{{'KDWS03_72' | i18n}}</button>
    </div>
</div>
</template>