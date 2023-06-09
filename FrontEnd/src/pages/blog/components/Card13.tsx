import { Link } from 'react-router-dom';
import NcImage from '~/components/NcImage/NcImage';
import PostTypeFeaturedIcon from '~/components/PostTypeFeaturedIcon/PostTypeFeaturedIcon';
import { PostCardMeta } from '~/components/Shared/PostCardMeta';
import { board } from '~/types/sharedTypes';
import { htmlToPlainText } from '~/utils/htmlToPlainText';

export interface Card13Props {
  post: board;
  className?: string;
}

export const Card13 = ({ post, className = '' }: Card13Props) => {
  const { id, title, content, thumbnailUrl, createdTime } = post;
  const date = new Date(createdTime as string);
  const formattedDate = date.toISOString().slice(0, 10);
  const url = thumbnailUrl !== null ? thumbnailUrl : 'noImg';

  return (
    <div className={`nc-Card13 relative flex border-b-2`} data-nc-id='Card13'>
      <div className='flex flex-col w-4/5 h-full py-2'>
        <h2 className={`nc-card-title block font-semibold text-base `}>
          <Link
            to={`/post/${formattedDate}/${id}`}
            state={{
              url,
              boardId: id,
            }}
            className='line-clamp-1'
            title={title}
          >
            {title}
          </Link>
        </h2>
        <span className='hidden my-3 sm:block text-neutral-500 dark:text-neutral-400 min-h-[32px]'>
          <Link
            to={`/post/${formattedDate}/${id}`}
            state={{
              url,
            }}
            className='line-clamp-2 text-ellipsis max-w-[300px] overflow-hidden'
          >
            {htmlToPlainText(content)}
          </Link>
        </span>
        <span className='block mt-4 mb-5 text-sm sm:hidden text-neutral-500'>2022.12.12</span>
        <div className='hidden mt-auto sm:block'>
          <PostCardMeta meta={{ ...post }} className='mt-8' />
        </div>
      </div>

      <Link
        to={`/post/${formattedDate}/${id}`}
        state={{
          url,
        }}
        className={` ${className} border-2 rounded-3xl`}
      >
        <NcImage
          containerClassName='absolute inset-0 '
          className='w-full h-full rounded-3xl object-fit'
          src={thumbnailUrl}
          alt={title}
        />
        <PostTypeFeaturedIcon
          className='absolute bottom-2 left-2'
          wrapSize='w-8 h-8'
          iconSize='w-4 h-4'
        />
      </Link>
    </div>
  );
};

export default Card13;
