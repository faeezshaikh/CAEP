package com.hcsc.caep.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.hcsc.caep.domaindto.ClaimDetail;
import com.hcsc.caep.domaindto.ClaimServiceLineDetail;
import com.hcsc.caep.domaindto.ClaimSummary;
import com.hcsc.caep.service.ClaimsService;
import com.hcsc.caep.transformers.ClaimsTransformer;

public class ClaimsServiceImpl implements ClaimsService {
	private CINQAdapter cinq;
	private ClaimsTransformer transformer;

	public ClaimsTransformer getTransformer() {
		return transformer;
	}

	public void setTransformer(ClaimsTransformer transformer) {
		this.transformer = transformer;
	}

	public CINQAdapter getCinq() {
		return cinq;
	}

	public void setCinq(CINQAdapter cinq) {
		this.cinq = cinq;
	}

	public Response getClaims(String subscriberId) {

		try {
			System.out.println("Subscriber number = " + subscriberId);

			List<ClaimSummary> claimSummaries = getClaimSummaries(subscriberId);
			return Response.status(Response.Status.OK)
						   .entity(claimSummaries)
						   .build();

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	private List<ClaimSummary> getClaimSummaries(String subscriberId) {
		return cinq.findClaims(subscriberId);
	}

	public Response getClaimDetail(String claimId) {
		ClaimDetail claimDetail = new ClaimDetail();
		claimDetail.setClaimDispositionDate("07/02/2014");
		claimDetail.setDateClaimPaid("06/05/2014");
		claimDetail.setDateClaimReceived("06/09/2014");
		claimDetail.setId(Long.valueOf(claimId));
		List<ClaimServiceLineDetail> list = new ArrayList<ClaimServiceLineDetail>();
		ClaimServiceLineDetail cslDetail = new ClaimServiceLineDetail();
		cslDetail.setBilledAmount(new BigDecimal("12345.87"));
		cslDetail.setDiagnosisCode("222");
		cslDetail.setFromDateOfService("07/01/2013");
		cslDetail.setPaidAmount(new BigDecimal("12345.87"));
		cslDetail.setPatientPortion(new BigDecimal("12345.87"));
		cslDetail.setPlaceOfService("Chicago, IL");
		cslDetail.setProcedureCode("123");
		cslDetail.setToDateOfService("07/02/2013");
		list.add(cslDetail);
		claimDetail.setList(list);
		claimDetail.setTotalAppliedToDeductible(new BigDecimal("12345.87"));
		claimDetail.setTotalBilledAmount(new BigDecimal("99987.87"));
		claimDetail.setTotalCoPaymentPaid(new BigDecimal("100"));
		claimDetail.setTotalPaid(new BigDecimal("999876.98"));
		claimDetail.setTotalPaidByCoinsurance(new BigDecimal("100"));
		claimDetail.setTotalPatientResponsibility(new BigDecimal("88888.87"));

		return Response.status(Response.Status.OK).entity(claimDetail).build();
	}

}
