package com.ssolutionsdeveloper.ecocycle.api;

import com.ssolutionsdeveloper.ecocycle.model.CNPJ;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CNPJService {

  @GET ("cnpj/"+"{cnpj}")
    Call <CNPJ> recuperarCNPJ(@Path("cnpj") String cnpj);
}
