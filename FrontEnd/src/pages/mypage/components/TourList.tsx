import { useQuery } from '@tanstack/react-query';
import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
import { getTourListApi } from '~/api/boardApi';
import { Spinner } from '~/components/Shared/Spinner';
import { ErrorPage } from '~/pages/Handle/ErrorPage';
import { tourList } from '~/types/sharedTypes';
import styled from 'styled-components';
import tw from 'twin.macro';
import { TourItem } from './TourItem';
import Pagination from 'react-js-pagination';

export const TourList = () => {
  // const navigate = useNavigate();
  const { data: tours, isLoading, error } = useQuery<tourList[]>(['tours'], getTourListApi);
  const [activePage, setActivePage] = useState(1);
  const toursPerPage = 1; // 한 페이지당 보여줄 여행 목록 개수
  const totalToursCount = tours?.length ?? 0; // 전체 여행 목록 개수
  const handlePageChange = (pageNumber: number) => setActivePage(pageNumber);

  // const handleClick = (id: string) => {
  //   console.log('click');
  //   navigate(`/createPost/${id}`);
  // };

  // const handleToudetail = async (id: string) => {
  //   //
  //   const res = await getTourByIdApi(id);
  //   console.log(res);
  // };

  if (isLoading) return <Spinner />;
  if (error) return <ErrorPage />;
  return (
    <S.Box>
      {tours?.length === 0 && <div>여행 기록이 없습니다.</div>}
      <S.TourItemBox>
        <h1>나의 여행</h1>
        {tours?.slice((activePage - 1) * toursPerPage, activePage * toursPerPage).map((tour) => (
          <TourItem key={tour.id} tourId={tour.id} />
        ))}
        <div className='flex justify-center mt-10'>
          <Pagination
            activePage={activePage}
            itemsCountPerPage={toursPerPage}
            totalItemsCount={totalToursCount}
            pageRangeDisplayed={5}
            onChange={handlePageChange}
            itemClass='inline-block px-4 py-2 border-2 rounded-full mx-1 hover:bg-red-100 hover:cursor-pointer hover:border-red-200' // Customize the pagination item
            linkClass='' // Customize the pagination link
            activeClass='bg-red-200 hover:bg-red-200 border-red-200' // Customize the active pagination item
            activeLinkClass='text-white' // Customize the active pagination link
            // disabledClass='text-gray-400 cursor-not-allowed' // Customize the disabled pagination item
          />
        </div>
      </S.TourItemBox>
    </S.Box>
  );
};

const S = {
  Box: styled.div`
    ${tw``}
  `,
  TourItemBox: styled.div`
    ${tw`max-w-[1200px] mx-auto`}
    h1 {
      ${tw`text-xl font-bold border-l-4 border-red-200 pl-3 font-nexon`}
    }
  `,
};
