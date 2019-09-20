<template>
<div class="kdws03d py-0">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close('NotCloseMenu')">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div class="text-truncate"><span>{{ params.employeeName }}</span></div>
    </div>
  </div>  
  <!-- D3_1: エラー表示 -->
  <div class="row uk-bg-light-coral py-2 pl-2">
    {{'KDWS03_40' | i18n}}    
  </div>  
  <div class="row">
    <!-- D3_1: エラー一覧 -->
    <table class="table table-bordered">
    <thead>
      <tr class="uk-bg-green">
        <!-- D3_2: 日付（ヘッダ）-->
        <th>{{'KDWS03_41' | i18n}}</th>
        <!-- D3_2: 日付（ヘッダ）-->
        <th>{{'KDWS03_42' | i18n}}</th>
        <!-- D3_2: 日付（ヘッダ）-->
        <th>{{'KDWS03_43' | i18n}}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(rowData, index) in rowDatas" v-bind:key="index" v-on:click="openDialogE(rowData)">
        <!-- D3_5: 日付-->
        <td class="pl-0 pr-0 text-center">{{ rowData.date | date('DD日(dd)') }}</td>
        <!-- D3_6: 日付-->
        <td class="pl-0 pr-0 text-center">{{ rowData.code }}</td>
        <td>
          <div class="row">
            <div class="w-100">
              <!-- D3_7: 日付-->
              <div class="col-10 pl-2 pr-2 d-inline-block">{{ rowData.name }}</div>
              <div class="d-inline-block fas fa-angle-right"></div>
            </div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  </div>
</div>
</template>