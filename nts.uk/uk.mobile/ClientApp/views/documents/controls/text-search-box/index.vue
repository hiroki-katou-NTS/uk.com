<template>
<div class="documentscontrolstext-search-box">
    <h5>1. {{'sample' | i18n}}</h5>
    <text-search-box 
        placeholder="勤務種類コード/勤務種類名称"
        v-on:search="searchList"/>
        
    <br/>
    <markdown />

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
    
</div>
</template>